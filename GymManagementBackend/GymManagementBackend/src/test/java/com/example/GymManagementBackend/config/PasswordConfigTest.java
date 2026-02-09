package com.example.GymManagementBackend.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordConfigTest {

    private final PasswordEncoder encoder = new PasswordConfig().passwordEncoder();

    @Test
    void shouldMatchBcryptHashes() {
        String hash = encoder.encode("secret123");
        assertTrue(encoder.matches("secret123", hash));
        assertFalse(encoder.matches("wrong-password", hash));
    }

    @Test
    void shouldMatchLegacyPlainTextValues() {
        assertTrue(encoder.matches("admin123", "admin123"));
        assertFalse(encoder.matches("admin123", "another-value"));
    }
}
