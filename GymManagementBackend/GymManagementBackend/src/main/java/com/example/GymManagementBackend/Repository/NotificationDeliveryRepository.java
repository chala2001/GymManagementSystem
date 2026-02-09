package com.example.GymManagementBackend.Repository;

import com.example.GymManagementBackend.Entity.NotificationDelivery;
import com.example.GymManagementBackend.Entity.NotificationTemplateType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationDeliveryRepository extends JpaRepository<NotificationDelivery, Long> {

    boolean existsByEventIdAndRecipientEmailAndTemplateType(
            String eventId,
            String recipientEmail,
            NotificationTemplateType templateType
    );
}
