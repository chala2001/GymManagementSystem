package com.example.GymManagementBackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return bcrypt.encode(rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                if (rawPassword == null || encodedPassword == null) {
                    return false;
                }

                if (isBcryptHash(encodedPassword)) {
                    return bcrypt.matches(rawPassword, encodedPassword);
                }

                if (encodedPassword.startsWith("{bcrypt}")) {
                    String value = encodedPassword.substring("{bcrypt}".length());
                    return bcrypt.matches(rawPassword, value);
                }

                if (encodedPassword.startsWith("{noop}")) {
                    String value = encodedPassword.substring("{noop}".length());
                    return rawPassword.toString().equals(value);
                }

                // Legacy fallback for old plain-text records in DB.
                return rawPassword.toString().equals(encodedPassword);
            }
        };
    }

    private static boolean isBcryptHash(String encodedPassword) {
        return encodedPassword.startsWith("$2a$")
                || encodedPassword.startsWith("$2b$")
                || encodedPassword.startsWith("$2y$");
    }
}
