package com.jsonplaceholder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDto {
    private String token;
    private String type = "Bearer";
    private UserDto user;

    public AuthResponseDto(String token, UserDto user) {
        this.token = token;
        this.user = user;
    }
} 