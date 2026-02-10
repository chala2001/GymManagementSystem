package com.example.GymManagementBackend.messaging;

import com.example.GymManagementBackend.Entity.AssignmentOutbox;
import com.example.GymManagementBackend.Entity.OutboxStatus;
import com.example.GymManagementBackend.Repository.AssignmentOutboxRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OutboxPublisherTest {

    @Test
    void publishPendingEventsShouldMarkAsPublished() {
        AssignmentOutboxRepository repository = mock(AssignmentOutboxRepository.class);
        KafkaTemplate<String, String> kafkaTemplate = mock(KafkaTemplate.class);
        OutboxPublisher publisher = new OutboxPublisher(repository, kafkaTemplate, "trainer.assignment.v1", 50);

        AssignmentOutbox entry = AssignmentOutbox.pending("event-1", "{\"eventId\":\"event-1\"}");
        when(repository.findByStatusOrderByCreatedAtAsc(any(), any(Pageable.class)))
                .thenReturn(List.of(entry));
        when(kafkaTemplate.send(anyString(), anyString(), anyString()))
                .thenReturn(CompletableFuture.completedFuture(mock(SendResult.class)));

        publisher.publishPendingEvents();

        assertEquals(OutboxStatus.PUBLISHED, entry.getStatus());
        verify(repository).saveAll(List.of(entry));
    }

    @Test
    void publishPendingEventsShouldIncrementRetryOnFailure() {
        AssignmentOutboxRepository repository = mock(AssignmentOutboxRepository.class);
        KafkaTemplate<String, String> kafkaTemplate = mock(KafkaTemplate.class);
        OutboxPublisher publisher = new OutboxPublisher(repository, kafkaTemplate, "trainer.assignment.v1", 50);

        AssignmentOutbox entry = AssignmentOutbox.pending("event-2", "{\"eventId\":\"event-2\"}");
        when(repository.findByStatusOrderByCreatedAtAsc(any(), any(Pageable.class)))
                .thenReturn(List.of(entry));
        when(kafkaTemplate.send(anyString(), anyString(), anyString()))
                .thenReturn(CompletableFuture.failedFuture(new RuntimeException("Kafka unavailable")));

        publisher.publishPendingEvents();

        assertEquals(OutboxStatus.PENDING, entry.getStatus());
        assertEquals(1, entry.getRetryCount());
        verify(repository).saveAll(any());
    }
}
