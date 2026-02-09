package com.example.GymManagementBackend.Controller;


import com.example.GymManagementBackend.Entity.Admin;
import com.example.GymManagementBackend.Entity.Trainer;
import com.example.GymManagementBackend.Entity.User;
import com.example.GymManagementBackend.Service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // Add trainer
    @PostMapping("/trainers")
    public Trainer createTrainer(@RequestBody Trainer trainer) {
        return adminService.createTrainer(trainer);
    }

    @PostMapping("/create-admin")
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminService.createAdmin(admin);
    }


    // Get all trainers
    @GetMapping("/trainers")
    public List<Trainer> getAllTrainers() {
        return adminService.getAllTrainers();
    }

    // Delete trainer
    @DeleteMapping("/trainers/{id}")
    public void deleteTrainer(@PathVariable Long id) {
        adminService.deleteTrainer(id);
    }

    // Get all users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return adminService.getAllUsers();
    }

    // Delete user
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
    }
}