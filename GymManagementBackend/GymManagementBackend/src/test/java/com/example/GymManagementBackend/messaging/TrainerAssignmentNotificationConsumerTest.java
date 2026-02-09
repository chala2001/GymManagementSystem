package com.example.GymManagementBackend.messaging;

import com.example.GymManagementBackend.Entity.NotificationDelivery;
import com.example.GymManagementBackend.Entity.NotificationTemplateType;
import com.example.GymManagementBackend.Repository.NotificationDeliveryRepository;
import com.example.GymManagementBackend.event.TrainerAssignmentEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TrainerAssignmentNotificationConsumerTest {

    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    @Test
    void handleTrainerAssignmentShouldSendTwoEmails() throws Exception {
        NotificationDeliveryRepository repository = mock(NotificationDeliveryRepository.class);
        JavaMailSender mailSender = mock(JavaMailSender.class);
        TrainerAssignmentNotificationConsumer consumer =
                new TrainerAssignmentNotificationConsumer(repository, mailSender, objectMapper, "no-reply@gym.local");

        TrainerAssignmentEvent event = buildEvent();
        String payload = objectMapper.writeValueAsString(event);

        when(repository.existsByEventIdAndRecipientEmailAndTemplateType(
                event.getEventId(),
                event.getUserEmail(),
                NotificationTemplateType.USER_ASSIGNMENT
        )).thenReturn(false);
        when(repository.existsByEventIdAndRecipientEmailAndTemplateType(
                event.getEventId(),
                event.getTrainerEmail(),
                NotificationTemplateType.TRAINER_ASSIGNMENT
        )).thenReturn(false);

        consumer.handleTrainerAssignment(payload);

        verify(mailSender, times(2)).send(any(org.springframework.mail.SimpleMailMessage.class));
        verify(repository, times(2)).save(any(NotificationDelivery.class));
    }

    @Test
    void handleTrainerAssignmentShouldSkipAlreadyDeliveredNotifications() throws Exception {
        NotificationDeliveryRepository repository = mock(NotificationDeliveryRepository.class);
        JavaMailSender mailSender = mock(JavaMailSender.class);
        TrainerAssignmentNotificationConsumer consumer =
                new TrainerAssignmentNotificationConsumer(repository, mailSender, objectMapper, "no-reply@gym.local");

        TrainerAssignmentEvent event = buildEvent();
        String payload = objectMapper.writeValueAsString(event);

        when(repository.existsByEventIdAndRecipientEmailAndTemplateType(
                event.getEventId(),
                event.getUserEmail(),
                NotificationTemplateType.USER_ASSIGNMENT
        )).thenReturn(true);
        when(repository.existsByEventIdAndRecipientEmailAndTemplateType(
                event.getEventId(),
                event.getTrainerEmail(),
                NotificationTemplateType.TRAINER_ASSIGNMENT
        )).thenReturn(true);

        consumer.handleTrainerAssignment(payload);

        verify(mailSender, never()).send(any(org.springframework.mail.SimpleMailMessage.class));
        verify(repository, never()).save(any(NotificationDelivery.class));
    }

    private TrainerAssignmentEvent buildEvent() {
        TrainerAssignmentEvent event = new TrainerAssignmentEvent();
        event.setEventId("event-123");
        event.setOccurredAt(LocalDateTime.now());
        event.setAssignmentId("assignment-1");
        event.setUserId(1L);
        event.setUserName("Member One");
        event.setUserEmail("member@example.com");
        event.setTrainerId(2L);
        event.setTrainerName("Trainer One");
        event.setTrainerEmail("trainer@example.com");
        event.setAssignedBy("adminUser");
        event.setSource("GymManagementBackend");
        return event;
    }
}
