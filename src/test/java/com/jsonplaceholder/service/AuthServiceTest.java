package com.jsonplaceholder.service;

import com.jsonplaceholder.dto.AuthRequestDto;
import com.jsonplaceholder.dto.AuthResponseDto;
import com.jsonplaceholder.dto.UserDto;
import com.jsonplaceholder.model.Auth;
import com.jsonplaceholder.model.User;
import com.jsonplaceholder.repository.AuthRepository;
import com.jsonplaceholder.repository.UserRepository;
import com.jsonplaceholder.security.JwtTokenProvider;
import com.jsonplaceholder.service.impl.AuthServiceImpl;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthServiceImpl authServiceImpl;

    private Auth testAuth;
    private Authentication testAuthentication;
    private AuthRequestDto testRequest;
    private UserDto testUserDto;

    @BeforeEach
    void setUp() {
        authRepository.deleteAll();
        userRepository.deleteAll();

        testAuth = new Auth();
        testAuth.setId(1L);
        testAuth.setUsername("Test User");
        testAuth.setEmail("test@example.com");
        testAuth.setPasswordHash("encodedPassword");

        testAuthentication = new UsernamePasswordAuthenticationToken(
            testAuth.getEmail(),
            "password",
            null
        );

        testRequest = new AuthRequestDto();
        testRequest.setUsername("Test User");
        testRequest.setEmail("test@example.com");
        testRequest.setPassword("password");

        testUserDto = new UserDto();
        testUserDto.setId(1L);
        testUserDto.setName("Test User");
        testUserDto.setEmail("test@example.com");
        testUserDto.setUsername("test@example.com");
    }

    @Test
    void register_ValidRequest_ReturnsTokenAndUser() {
        // Arrange
        AuthRequestDto request = new AuthRequestDto();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("password123");

        // Act
        AuthResponseDto response = authService.register(request);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getToken());
        assertNotNull(response.getUser());
        assertEquals("test@example.com", response.getUser().getEmail());

        // Verify database
        Auth savedAuth = authRepository.findByEmail("test@example.com").orElse(null);
        assertNotNull(savedAuth);
        assertEquals("testuser", savedAuth.getUsername());
    }

    @Test
    void register_DuplicateEmail_ThrowsException() {
        // Arrange
        AuthRequestDto request = new AuthRequestDto();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("password123");

        // Create existing user
        authService.register(request);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> authService.register(request));
    }

    @Test
    void login_ValidCredentials_ReturnsTokenAndUser() {
        // Arrange
        AuthRequestDto registerRequest = new AuthRequestDto();
        registerRequest.setUsername("testuser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");

        // Register user
        authService.register(registerRequest);

        // Act
        AuthResponseDto response = authService.login(registerRequest);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getToken());
        assertNotNull(response.getUser());
        assertEquals("test@example.com", response.getUser().getEmail());
    }

    @Test
    void login_InvalidCredentials_ThrowsException() {
        // Arrange
        AuthRequestDto request = new AuthRequestDto();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("wrongpassword");

        // Act & Assert
        assertThrows(Exception.class, () -> authService.login(request));
    }
} 