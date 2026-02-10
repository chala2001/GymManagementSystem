package com.example.GymManagementBackend.messaging;

import com.example.GymManagementBackend.Entity.NotificationDelivery;
import com.example.GymManagementBackend.Entity.NotificationTemplateType;
import com.example.GymManagementBackend.Repository.NotificationDeliveryRepository;
import com.example.GymManagementBackend.event.TrainerAssignmentEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.kafka.annotation.BackOff;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class TrainerAssignmentNotificationConsumer {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(TrainerAssignmentNotificationConsumer.class);

    private final NotificationDeliveryRepository notificationDeliveryRepository;
    private final JavaMailSender javaMailSender;
    private final ObjectMapper objectMapper;
    private final String fromEmail;

    public TrainerAssignmentNotificationConsumer(
            NotificationDeliveryRepository notificationDeliveryRepository,
            JavaMailSender javaMailSender,
            ObjectMapper objectMapper,
            @Value("${app.notification.from-email}") String fromEmail
    ) {
        this.notificationDeliveryRepository = notificationDeliveryRepository;
        this.javaMailSender = javaMailSender;
        this.objectMapper = objectMapper;
        this.fromEmail = fromEmail;
    }

    @RetryableTopic(
            attempts = "${app.kafka.retry.attempts:4}",
            backOff = @BackOff(
                    delayString = "${app.kafka.retry.delay-ms:3000}",
                    multiplierString = "${app.kafka.retry.multiplier:2.0}"
            ),
            retryTopicSuffix = ".retry",
            dltTopicSuffix = ".dlt"
    )
    @KafkaListener(
            topics = "${app.kafka.topics.trainer-assignment}",
            groupId = "${app.kafka.consumer-group}"
    )
    public void handleTrainerAssignment(String payload) {
        TrainerAssignmentEvent event = parsePayload(payload);

        sendIfNotDelivered(
                event.getEventId(),
                event.getUserEmail(),
                NotificationTemplateType.USER_ASSIGNMENT,
                "Your trainer has been assigned",
                buildUserEmailBody(event)
        );

        sendIfNotDelivered(
                event.getEventId(),
                event.getTrainerEmail(),
                NotificationTemplateType.TRAINER_ASSIGNMENT,
                "A new member has been assigned to you",
                buildTrainerEmailBody(event)
        );
    }

    @DltHandler
    public void handleDlt(String payload) {
        LOGGER.error("Message routed to DLT: {}", payload);
    }

    private TrainerAssignmentEvent parsePayload(String payload) {
        try {
            return objectMapper.readValue(payload, TrainerAssignmentEvent.class);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException("Invalid trainer assignment event payload", exception);
        }
    }

    private void sendIfNotDelivered(
            String eventId,
            String recipientEmail,
            NotificationTemplateType templateType,
            String subject,
            String body
    ) {
        boolean alreadyDelivered = notificationDeliveryRepository
                .existsByEventIdAndRecipientEmailAndTemplateType(eventId, recipientEmail, templateType);
        if (alreadyDelivered) {
            return;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(recipientEmail);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);

        try {
            notificationDeliveryRepository.save(
                    NotificationDelivery.delivered(eventId, recipientEmail, templateType)
            );
        } catch (DataIntegrityViolationException exception) {
            // Another consumer attempt won the race; treat as already delivered.
            LOGGER.info(
                    "Duplicate notification delivery ignored for eventId={}, recipient={}, template={}",
                    eventId,
                    recipientEmail,
                    templateType
            );
        }
    }

    private String buildUserEmailBody(TrainerAssignmentEvent event) {
        return String.format(
                "Hi %s,%n%nYour trainer has been assigned.%n%nTrainer: %s%nTrainer email: %s%nAssigned by: %s%nAssignment ID: %s%n%nThank you,%nGym Management Team",
                event.getUserName(),
                event.getTrainerName(),
                event.getTrainerEmail(),
                event.getAssignedBy(),
                event.getAssignmentId()
        );
    }

    private String buildTrainerEmailBody(TrainerAssignmentEvent event) {
        return String.format(
                "Hi %s,%n%nA new member has been assigned to you.%n%nMember: %s%nMember email: %s%nAssigned by: %s%nAssignment ID: %s%n%nThank you,%nGym Management Team",
                event.getTrainerName(),
                event.getUserName(),
                event.getUserEmail(),
                event.getAssignedBy(),
                event.getAssignmentId()
        );
    }
}
