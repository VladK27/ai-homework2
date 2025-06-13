package com.jsonplaceholder.security;

import com.jsonplaceholder.model.Auth;
import com.jsonplaceholder.repository.AuthRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class CustomUserDetailsServiceTest {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthRepository authRepository;

    @BeforeEach
    void setUp() {
        authRepository.deleteAll();
    }

    @Test
    void loadUserByUsername_ExistingUser_ReturnsUserDetails() {
        // Arrange
        Auth auth = new Auth();
        auth.setUsername("testuser");
        auth.setEmail("test@example.com");
        auth.setPasswordHash("$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG");
        authRepository.save(auth);

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername("test@example.com");

        // Assert
        assertNotNull(userDetails);
        assertEquals("test@example.com", userDetails.getUsername());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")));
    }

    @Test
    void loadUserByUsername_NonExistingUser_ThrowsException() {
        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () ->
                userDetailsService.loadUserByUsername("nonexistent@example.com"));
    }

    @Test
    void loadUserByUsername_NullUsername_ThrowsException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                userDetailsService.loadUserByUsername(null));
    }

    @Test
    void loadUserByUsername_EmptyUsername_ThrowsException() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                userDetailsService.loadUserByUsername(""));
    }
} 