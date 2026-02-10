package com.example.GymManagementBackend.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "notification_delivery",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_event_recipient_template",
                        columnNames = {"eventId", "recipientEmail", "templateType"}
                )
        }
)
public class NotificationDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String eventId;

    @Column(nullable = false)
    private String recipientEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private NotificationTemplateType templateType;

    @Column(nullable = false)
    private LocalDateTime deliveredAt;

    public static NotificationDelivery delivered(
            String eventId,
            String recipientEmail,
            NotificationTemplateType templateType
    ) {
        NotificationDelivery delivery = new NotificationDelivery();
        delivery.setEventId(eventId);
        delivery.setRecipientEmail(recipientEmail);
        delivery.setTemplateType(templateType);
        delivery.setDeliveredAt(LocalDateTime.now());
        return delivery;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public NotificationTemplateType getTemplateType() {
        return templateType;
    }

    public void setTemplateType(NotificationTemplateType templateType) {
        this.templateType = templateType;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }
}
