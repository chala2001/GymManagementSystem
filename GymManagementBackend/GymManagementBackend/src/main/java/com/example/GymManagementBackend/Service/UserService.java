package com.example.GymManagementBackend.Service;

import com.example.GymManagementBackend.Entity.User;
import com.example.GymManagementBackend.Repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    // Register user anyone can register as an user
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setLastModifiedDate(LocalDateTime.now());
        return userRepository.save(user);
    }


    // View own profile
//    public User getUser(Long userId) {
//        return userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//    }

    //only the logged user can get the his user details only
    public User getUser(Long userId) {
        String loggedUsername =
                SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getUsername().equals(loggedUsername)) {
            throw new RuntimeException("Access denied");
        }

        return user;
    }


    // Update own profile (no trainer modification)
//    public User updateUser(Long userId, User updatedUser) {
//        User user = userRepository.findById(userId)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        user.setHeight(updatedUser.getHeight());
//        user.setWeight(updatedUser.getWeight());
//        user.setFatPercentage(updatedUser.getFatPercentage());
//        user.setTdee(updatedUser.getTdee());
//        user.setCaloriesPerDay(updatedUser.getCaloriesPerDay());
//
//        user.setLastModifiedDate(LocalDateTime.now());
//
//        return userRepository.save(user);
//    }

// only the logged user only can update their profiles
    public User updateUser(Long userId, User updatedUser) {
        String loggedUsername =
                SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getUsername().equals(loggedUsername)) {
            throw new RuntimeException("Access denied");
        }

        user.setHeight(updatedUser.getHeight());
        user.setWeight(updatedUser.getWeight());
        user.setFatPercentage(updatedUser.getFatPercentage());
        user.setTdee(updatedUser.getTdee());
        user.setCaloriesPerDay(updatedUser.getCaloriesPerDay());

        user.setLastModifiedDate(LocalDateTime.now());

        return userRepository.save(user);
    }

}