package com.example.GymManagementBackend.security;


import com.example.GymManagementBackend.Entity.Admin;
import com.example.GymManagementBackend.Entity.Trainer;
import com.example.GymManagementBackend.Entity.User;
import com.example.GymManagementBackend.Repository.AdminRepository;
import com.example.GymManagementBackend.Repository.TrainerRepository;
import com.example.GymManagementBackend.Repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final TrainerRepository trainerRepository;
    private final UserRepository userRepository;

    public CustomUserDetailsService(AdminRepository adminRepository,
                                    TrainerRepository trainerRepository,
                                    UserRepository userRepository) {
        this.adminRepository = adminRepository;
        this.trainerRepository = trainerRepository;
        this.userRepository = userRepository;
    }



    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        System.out.println("🔐 LOGIN ATTEMPT FOR: " + username);

        // 1️⃣ Check Admin
        Admin admin = adminRepository.findByUsername(username).orElse(null);
        if (admin != null) {
            return new org.springframework.security.core.userdetails.User(
                    admin.getUsername(),
                    admin.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        // 2️⃣ Check Trainer
        Trainer trainer = trainerRepository.findByUsername(username).orElse(null);
        if (trainer != null) {
            return new org.springframework.security.core.userdetails.User(
                    trainer.getUsername(),
                    trainer.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_TRAINER"))
            );
        }

        // 3️⃣ Check User
        User user = userRepository.findByUsername(username).orElse(null);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    List.of(new SimpleGrantedAuthority("ROLE_USER"))
            );
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}
