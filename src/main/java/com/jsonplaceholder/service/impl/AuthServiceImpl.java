package com.jsonplaceholder.service.impl;

import com.jsonplaceholder.dto.AuthRequestDto;
import com.jsonplaceholder.dto.AuthResponseDto;
import com.jsonplaceholder.dto.UserDto;
import com.jsonplaceholder.model.Auth;
import com.jsonplaceholder.model.User;
import com.jsonplaceholder.repository.AuthRepository;
import com.jsonplaceholder.repository.UserRepository;
import com.jsonplaceholder.security.JwtTokenProvider;
import com.jsonplaceholder.service.AuthService;
import com.jsonplaceholder.service.UserService;
import jakarta.persistence.EntityExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.dao.DataIntegrityViolationException;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    public AuthServiceImpl(
            AuthRepository authRepository,
            UserRepository userRepository,
            UserService userService,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager,
            JwtTokenProvider tokenProvider) {
        this.authRepository = authRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public AuthResponseDto register(AuthRequestDto request) {
        if (authRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists: " + request.getEmail());
        }
        try {
            // Create auth record
            Auth auth = new Auth();
            auth.setUsername(request.getUsername());
            auth.setEmail(request.getEmail());
            auth.setPasswordHash(passwordEncoder.encode(request.getPassword()));
            authRepository.save(auth);

            // Create user record
            UserDto userDto = new UserDto();
            userDto.setName(request.getUsername());
            userDto.setEmail(request.getEmail());
            userDto.setUsername(request.getUsername());
            userDto.setPhone(""); // Default values
            userDto.setWebsite("");
            UserDto savedUser = userService.createUser(userDto);

            // Authenticate and generate token
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = tokenProvider.generateToken(authentication);

            return new AuthResponseDto(token, savedUser);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists: " + request.getEmail());
        }
    }

    @Override
    public AuthResponseDto login(AuthRequestDto request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);

        UserDto user = userService.getUserByEmail(request.getEmail());
        return new AuthResponseDto(token, user);
    }
} 