package com.example.GymManagementBackend.Service;


import com.example.GymManagementBackend.Entity.Admin;
import com.example.GymManagementBackend.Entity.Trainer;
import com.example.GymManagementBackend.Entity.User;
import com.example.GymManagementBackend.Repository.AdminRepository;
import com.example.GymManagementBackend.Repository.TrainerRepository;
import com.example.GymManagementBackend.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    private final PasswordEncoder passwordEncoder;


    public AdminService(TrainerRepository trainerRepository,
                        UserRepository userRepository,
                        PasswordEncoder passwordEncoder,AdminRepository adminRepository) {
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminRepository=adminRepository;
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
}