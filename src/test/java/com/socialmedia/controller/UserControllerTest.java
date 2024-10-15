package com.socialmedia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialmedia.entity.User;
import com.socialmedia.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)  // Specifies that we're testing the UserController
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        // Initialize Mockito
        MockitoAnnotations.openMocks(this);

        // Create a sample User entity
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
    }

    @Test
    void testGetAllUsers() throws Exception {
        // Arrange
        List<User> users = Arrays.asList(user);
        when(userService.getAllUsers()).thenReturn(users);

        // Act & Assert
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(user.getId()))
                .andExpect(jsonPath("$[0].username").value(user.getUsername()))
                .andExpect(jsonPath("$[0].email").value(user.getEmail()));

        // Verify that the service method was called once
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById() throws Exception {
        // Arrange
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(Optional.of(user));

        // Act & Assert
        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));

        // Verify that the service method was called once
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void testGetUserById_NotFound() throws Exception {
        // Arrange
        Long userId = 1L;
        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isNotFound());

        // Verify that the service method was called once
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void testCreateUser() throws Exception {
        // Arrange
        when(userService.saveUser(any(User.class))).thenReturn(user);

        // Convert User entity to JSON
        ObjectMapper mapper = new ObjectMapper();
        String userJson = mapper.writeValueAsString(user);

        // Act & Assert
        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.username").value(user.getUsername()))
                .andExpect(jsonPath("$.email").value(user.getEmail()));

        // Verify that the service method was called once
        verify(userService, times(1)).saveUser(any(User.class));
    }

    @Test
    void testDeleteUser() throws Exception {
        // Arrange
        Long userId = 1L;

        // Act & Assert
        mockMvc.perform(delete("/api/users/{id}", userId))
                .andExpect(status().isNoContent());

        // Verify that the service method was called once
        verify(userService, times(1)).deleteUser(userId);
    }
}
