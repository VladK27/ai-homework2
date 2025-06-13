package com.jsonplaceholder.service;

import com.jsonplaceholder.dto.AuthRequestDto;
import com.jsonplaceholder.dto.AuthResponseDto;

public interface AuthService {
    AuthResponseDto register(AuthRequestDto request);
    AuthResponseDto login(AuthRequestDto request);
} 