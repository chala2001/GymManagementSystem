package com.example.GymManagementBackend.messaging;

import com.example.GymManagementBackend.Entity.AssignmentOutbox;
import com.example.GymManagementBackend.Entity.OutboxStatus;
import com.example.GymManagementBackend.Repository.AssignmentOutboxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnProperty(
        value = "app.outbox.publisher.enabled",
        havingValue = "true",
        matchIfMissing = true
)
public class OutboxPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutboxPublisher.class);

    private final AssignmentOutboxRepository assignmentOutboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String trainerAssignmentTopic;
    private final int batchSize;

    public OutboxPublisher(
            AssignmentOutboxRepository assignmentOutboxRepository,
            KafkaTemplate<String, String> kafkaTemplate,
            @Value("${app.kafka.topics.trainer-assignment}") String trainerAssignmentTopic,
            @Value("${app.outbox.publisher.batch-size:50}") int batchSize
    ) {
        this.assignmentOutboxRepository = assignmentOutboxRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.trainerAssignmentTopic = trainerAssignmentTopic;
        this.batchSize = batchSize;
    }

    @Transactional
    @Scheduled(fixedDelayString = "${app.outbox.publisher.fixed-delay-ms:5000}")
    public void publishPendingEvents() {
        List<AssignmentOutbox> pendingEntries =
                assignmentOutboxRepository.findByStatusOrderByCreatedAtAsc(
                        OutboxStatus.PENDING,
                        PageRequest.of(0, batchSize)
                );

        if (pendingEntries.isEmpty()) {
            return;
        }

        for (AssignmentOutbox entry : pendingEntries) {
            try {
                kafkaTemplate
                        .send(trainerAssignmentTopic, entry.getEventId(), entry.getPayload())
                        .get(10, TimeUnit.SECONDS);
                entry.markPublished();
            } catch (Exception exception) {
                String errorMessage = exception.getMessage();
                entry.markFailed(errorMessage == null ? "Unknown Kafka publish error" : errorMessage);
                LOGGER.error("Failed to publish outbox eventId={}", entry.getEventId(), exception);
            }
        }

        assignmentOutboxRepository.saveAll(pendingEntries);
    }
}
