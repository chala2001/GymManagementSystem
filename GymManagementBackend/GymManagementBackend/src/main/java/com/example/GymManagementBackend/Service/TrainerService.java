package com.example.GymManagementBackend.Service;

import com.example.GymManagementBackend.Entity.Trainer;
import com.example.GymManagementBackend.Entity.User;
import com.example.GymManagementBackend.Repository.TrainerRepository;
import com.example.GymManagementBackend.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;

    public TrainerService(TrainerRepository trainerRepository,
                          UserRepository userRepository) {
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
    }

    // Update trainer profile
    public Trainer updateTrainerProfile(Long trainerId, Trainer updatedTrainer) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        trainer.setName(updatedTrainer.getName());
        trainer.setExperienceYears(updatedTrainer.getExperienceYears());
        trainer.setDateOfBirth(updatedTrainer.getDateOfBirth());

        return trainerRepository.save(trainer);
    }

    // Get assigned users
    public List<User> getAssignedUsers(Long trainerId) {
        return userRepository.findByTrainerId(trainerId);
    }

    // Assign user to trainer
    public User assignUserToTrainer(Long trainerId, Long userId) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setTrainer(trainer);
        user.setLastModifiedDate(LocalDateTime.now());

        return userRepository.save(user);
    }

    // Update workout routine of assigned user
    public User updateUserRoutine(Long trainerId, Long userId, String routine) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getTrainer() == null ||
                !user.getTrainer().getId().equals(trainerId)) {
            throw new RuntimeException("Access denied: not your client");
        }

        user.setGymRoutine(routine);
        user.setLastModifiedDate(LocalDateTime.now());

        return userRepository.save(user);
    }
}