package com.example.GymManagementBackend.Service;

import com.example.GymManagementBackend.Entity.Admin;
import com.example.GymManagementBackend.Entity.AssignmentOutbox;
import com.example.GymManagementBackend.Entity.Trainer;
import com.example.GymManagementBackend.Entity.User;
import com.example.GymManagementBackend.Repository.AdminRepository;
import com.example.GymManagementBackend.Repository.AssignmentOutboxRepository;
import com.example.GymManagementBackend.Repository.TrainerRepository;
import com.example.GymManagementBackend.Repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AdminServiceTest {

    private TrainerRepository trainerRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private AdminRepository adminRepository;
    private AssignmentOutboxRepository assignmentOutboxRepository;
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        trainerRepository = mock(TrainerRepository.class);
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        adminRepository = mock(AdminRepository.class);
        assignmentOutboxRepository = mock(AssignmentOutboxRepository.class);
        adminService = new AdminService(
                trainerRepository,
                userRepository,
                passwordEncoder,
                adminRepository,
                assignmentOutboxRepository,
                new ObjectMapper().findAndRegisterModules()
        );
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void assignUserToTrainerShouldPersistOutboxEvent() {
        User user = new User("member", "p", "Member One", "member@example.com", LocalDate.now());
        user.setId(10L);
        Trainer trainer = new Trainer("trainer", "p", "Trainer One", "trainer@example.com", LocalDate.now(), 5);
        trainer.setId(20L);

        when(userRepository.findById(10L)).thenReturn(Optional.of(user));
        when(trainerRepository.findById(20L)).thenReturn(Optional.of(trainer));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(assignmentOutboxRepository.save(any(AssignmentOutbox.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        SecurityContextHolder.getContext()
                .setAuthentication(new UsernamePasswordAuthenticationToken("adminUser", "pw"));

        User savedUser = adminService.assignUserToTrainer(10L, 20L);

        ArgumentCaptor<AssignmentOutbox> outboxCaptor = ArgumentCaptor.forClass(AssignmentOutbox.class);
        verify(assignmentOutboxRepository).save(outboxCaptor.capture());

        AssignmentOutbox outbox = outboxCaptor.getValue();
        assertNotNull(outbox.getEventId());
        assertNotNull(outbox.getPayload());
        assertEquals(trainer, savedUser.getTrainer());

        var order = inOrder(userRepository, assignmentOutboxRepository);
        order.verify(userRepository).save(any(User.class));
        order.verify(assignmentOutboxRepository).save(any(AssignmentOutbox.class));
    }

    @Test
    void createAdminShouldEncodePassword() {
        Admin admin = new Admin();
        admin.setPassword("raw");
        when(passwordEncoder.encode("raw")).thenReturn("encoded");
        when(adminRepository.save(any(Admin.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Admin saved = adminService.createAdmin(admin);

        assertEquals("encoded", saved.getPassword());
    }
}
