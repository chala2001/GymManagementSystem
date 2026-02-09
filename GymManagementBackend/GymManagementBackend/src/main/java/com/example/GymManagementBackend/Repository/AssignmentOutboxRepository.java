package com.example.GymManagementBackend.Repository;

import com.example.GymManagementBackend.Entity.AssignmentOutbox;
import com.example.GymManagementBackend.Entity.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AssignmentOutboxRepository extends JpaRepository<AssignmentOutbox, Long> {

    List<AssignmentOutbox> findByStatusOrderByCreatedAtAsc(OutboxStatus status, Pageable pageable);
}
