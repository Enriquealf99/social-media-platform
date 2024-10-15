package com.socialmedia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialmedia.DTO.PostUpdateDTO;
import com.socialmedia.entity.Post;
import com.socialmedia.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    private Post post;

    @BeforeEach
    void setUp() {
        // Create a sample Post entity
        post = new Post();
        post.setId(1L);
        post.setTitle("Sample Post");
        post.setContent("This is a sample post.");
    }

    @Test
    void testCreatePost() throws Exception {
        Long userId = 1L;

        // Mock the service method
        when(postService.createPost(any(Post.class), eq(userId))).thenReturn(post);

        // Convert Post entity to JSON
        ObjectMapper mapper = new ObjectMapper();
        String postJson = mapper.writeValueAsString(post);

        // Perform POST request and verify the response
        mockMvc.perform(post("/api/posts")
                        .param("userid", String.valueOf(userId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.content").value(post.getContent()));
    }

    @Test
    void testGetAllPosts() throws Exception {
        // Mock the service method
        List<Post> posts = Arrays.asList(post);
        when(postService.getAllPosts()).thenReturn(posts);

        // Perform GET request and verify the response
        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(post.getId()))
                .andExpect(jsonPath("$[0].title").value(post.getTitle()))
                .andExpect(jsonPath("$[0].content").value(post.getContent()));
    }

    @Test
    void testGetPostById() throws Exception {
        Long postId = 1L;

        // Mock the service method
        when(postService.findPostById(postId)).thenReturn(Optional.of(post));

        // Perform GET request and verify the response
        mockMvc.perform(get("/api/posts/{post_id}", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.content").value(post.getContent()));
    }

    @Test
    void testUpdatePost() throws Exception {
        Long postId = 1L;

        // Create a sample DTO for update
        PostUpdateDTO postUpdateDTO = new PostUpdateDTO();
        postUpdateDTO.setTitle("Updated Title");
        postUpdateDTO.setContent("Updated content");

        // Mock the service method
        when(postService.updatePost(eq(postId), any(PostUpdateDTO.class))).thenReturn(post);

        // Convert PostUpdateDTO to JSON
        ObjectMapper mapper = new ObjectMapper();
        String postUpdateJson = mapper.writeValueAsString(postUpdateDTO);

        // Perform PUT request and verify the response
        mockMvc.perform(put("/api/posts/{post_id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(postUpdateJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value(post.getTitle()))
                .andExpect(jsonPath("$.content").value(post.getContent()));
    }

    @Test
    void testDeletePost() throws Exception {
        Long postId = 1L;

        // Mock the service method - no need to return anything for a void method
        doNothing().when(postService).deletePost(postId);

        // Perform DELETE request and verify the response
        mockMvc.perform(delete("/api/posts/{post_id}", postId))
                .andExpect(status().isNoContent());  // Expect 204 No Content status
    }


    @Test
    void testGetPostsByUser() throws Exception {
        Long userId = 1L;

        // Mock the service method
        List<Post> posts = Arrays.asList(post);
        when(postService.findAllPostsByUserId(userId)).thenReturn(posts);

        // Perform GET request and verify the response
        mockMvc.perform(get("/api/posts/user/{user_id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(post.getId()))
                .andExpect(jsonPath("$[0].title").value(post.getTitle()))
                .andExpect(jsonPath("$[0].content").value(post.getContent()));
    }
}

