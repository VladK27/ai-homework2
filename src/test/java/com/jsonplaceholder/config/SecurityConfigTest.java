package com.jsonplaceholder.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void publicEndpoints_ShouldBeAccessible() throws Exception {
        // Test registration endpoint
        mockMvc.perform(post("/api/auth/register")
                .contentType("application/json")
                .content("{\"username\":\"test\",\"email\":\"test@example.com\",\"password\":\"password123\"}"))
                .andExpect(status().isOk());

        // Test login endpoint
        mockMvc.perform(post("/api/auth/login")
                .contentType("application/json")
                .content("{\"email\":\"test@example.com\",\"password\":\"password123\"}"))
                .andExpect(status().isUnauthorized()); // Unauthorized because user doesn't exist yet

        // Test error endpoint
        mockMvc.perform(get("/error"))
                .andExpect(status().isOk());
    }

    @Test
    void protectedEndpoints_ShouldRequireAuthentication() throws Exception {
        // Test users endpoint without authentication
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isForbidden());

        // Test specific user endpoint without authentication
        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    void swaggerEndpoints_ShouldBeAccessible() throws Exception {
        // Test Swagger UI endpoint
        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk());

        // Test OpenAPI documentation endpoint
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk());
    }
} 