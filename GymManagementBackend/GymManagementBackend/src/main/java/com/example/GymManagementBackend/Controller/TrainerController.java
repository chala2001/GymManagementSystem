package com.example.GymManagementBackend.Controller;


import com.example.GymManagementBackend.Entity.Trainer;
import com.example.GymManagementBackend.Entity.User;
import com.example.GymManagementBackend.Service.TrainerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainer")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    // Update trainer profile
    @PutMapping("/{trainerId}")
    public Trainer updateTrainer(
            @PathVariable Long trainerId,
            @RequestBody Trainer trainer) {
        return trainerService.updateTrainerProfile(trainerId, trainer);
    }

    // Get assigned users
    @GetMapping("/{trainerId}/users")
    public List<User> getAssignedUsers(@PathVariable Long trainerId) {
        return trainerService.getAssignedUsers(trainerId);
    }

    // Assign user to trainer
    @PutMapping("/{trainerId}/assign/{userId}")
    public User assignUser(
            @PathVariable Long trainerId,
            @PathVariable Long userId) {
        return trainerService.assignUserToTrainer(trainerId, userId);
    }

    // Update workout routine
    @PutMapping("/{trainerId}/users/{userId}/routine")
    public User updateRoutine(
            @PathVariable Long trainerId,
            @PathVariable Long userId,
            @RequestBody String routine) {
        return trainerService.updateUserRoutine(trainerId, userId, routine);
    }
}
