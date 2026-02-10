package com.example.GymManagementBackend.Service;


import com.example.GymManagementBackend.Entity.AssignmentOutbox;
import com.example.GymManagementBackend.Entity.Admin;
import com.example.GymManagementBackend.Entity.Trainer;
import com.example.GymManagementBackend.Entity.User;
import com.example.GymManagementBackend.Repository.AssignmentOutboxRepository;
import com.example.GymManagementBackend.Repository.AdminRepository;
import com.example.GymManagementBackend.Repository.TrainerRepository;
import com.example.GymManagementBackend.Repository.UserRepository;
import com.example.GymManagementBackend.event.TrainerAssignmentEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AdminService {

    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final AssignmentOutboxRepository assignmentOutboxRepository;

    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;


    public AdminService(TrainerRepository trainerRepository,
                        UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        AdminRepository adminRepository,
                        AssignmentOutboxRepository assignmentOutboxRepository,
                        ObjectMapper objectMapper) {
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
        this.assignmentOutboxRepository = assignmentOutboxRepository;
        this.objectMapper = objectMapper;
    }

    public Admin createAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }


    // -------- Trainer Management --------

    public Trainer createTrainer(Trainer trainer) {
        trainer.setPassword(passwordEncoder.encode(trainer.getPassword()));
        return trainerRepository.save(trainer);

    }

    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    public void deleteTrainer(Long trainerId) {
        trainerRepository.deleteById(trainerId);
    }

    // -------- User Management --------

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public User assignUserToTrainer(Long userId, Long trainerId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        LocalDateTime now = LocalDateTime.now();
        user.setTrainer(trainer);
        user.setLastModifiedDate(now);

        User savedUser = userRepository.save(user);

        String eventId = UUID.randomUUID().toString();
        String assignmentId = String.format(
                "assignment-%d-%d-%d",
                savedUser.getId(),
                trainer.getId(),
                System.currentTimeMillis()
        );

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String assignedBy = authentication != null ? authentication.getName() : "SYSTEM";

        TrainerAssignmentEvent event = new TrainerAssignmentEvent();
        event.setEventId(eventId);
        event.setOccurredAt(now);
        event.setAssignmentId(assignmentId);
        event.setUserId(savedUser.getId());
        event.setUserName(savedUser.getName());
        event.setUserEmail(savedUser.getEmail());
        event.setTrainerId(trainer.getId());
        event.setTrainerName(trainer.getName());
        event.setTrainerEmail(trainer.getEmail());
        event.setAssignedBy(assignedBy);
        event.setSource("GymManagementBackend");

        String payload;
        try {
            payload = objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException("Failed to serialize assignment event payload", exception);
        }

        AssignmentOutbox outboxEntry = AssignmentOutbox.pending(eventId, payload);
        assignmentOutboxRepository.save(outboxEntry);

        return savedUser;
    }
}
