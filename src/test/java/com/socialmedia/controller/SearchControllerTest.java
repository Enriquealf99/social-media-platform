package com.socialmedia.controller;

import com.socialmedia.entity.Post;
import com.socialmedia.entity.User;
import com.socialmedia.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SearchController.class)
public class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchService searchService;

    private User user;
    private Post post;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create a sample User entity
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");

        // Create a sample Post entity
        post = new Post();
        post.setId(1L);
        post.setTitle("Sample Post");
        post.setContent("This is a sample post content.");
    }

    @Test
    void testSearchUsers() throws Exception {
        String username = "testuser";

        // Arrange: Mock the service method
        List<User> users = Arrays.asList(user);
        when(searchService.searchUsersByUsername(eq(username))).thenReturn(users);

        // Act & Assert
        mockMvc.perform(get("/api/search/users")
                        .param("username", username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(user.getId()))
                .andExpect(jsonPath("$[0].username").value(user.getUsername()))
                .andExpect(jsonPath("$[0].email").value(user.getEmail()));

        // Verify that the service method was called once
        verify(searchService, times(1)).searchUsersByUsername(eq(username));
    }

    @Test
    void testSearchPostsByContent() throws Exception {
        String content = "sample";

        // Arrange: Mock the service method
        List<Post> posts = Arrays.asList(post);
        when(searchService.searchPostsByContent(eq(content))).thenReturn(posts);

        // Act & Assert
        mockMvc.perform(get("/api/search/posts/content")
                        .param("content", content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(post.getId()))
                .andExpect(jsonPath("$[0].title").value(post.getTitle()))
                .andExpect(jsonPath("$[0].content").value(post.getContent()));

        // Verify that the service method was called once
        verify(searchService, times(1)).searchPostsByContent(eq(content));
    }

    @Test
    void testSearchPostsByTags() throws Exception {
        String tags = "java";

        // Arrange: Mock the service method
        List<Post> posts = Arrays.asList(post);
        when(searchService.searchPostsByTags(eq(tags))).thenReturn(posts);

        // Act & Assert
        mockMvc.perform(get("/api/search/posts/tags")
                        .param("tags", tags)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(post.getId()))
                .andExpect(jsonPath("$[0].title").value(post.getTitle()))
                .andExpect(jsonPath("$[0].content").value(post.getContent()));

        // Verify that the service method was called once
        verify(searchService, times(1)).searchPostsByTags(eq(tags));
    }
}

