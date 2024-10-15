package com.socialmedia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialmedia.DTO.CommentDTO;
import com.socialmedia.entity.Comment;
import com.socialmedia.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)  // Specifies that we're testing the CommentController
public class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;  // Mock the CommentService to be used in tests

    private Comment comment;
    private CommentDTO commentDTO;

    @BeforeEach
    void setUp() {
        // Create a sample Comment entity
        comment = new Comment();
        comment.setId(1L);
        comment.setContent("This is a test comment.");

        // Create a sample CommentDTO
        commentDTO = new CommentDTO();
        commentDTO.setContent("This is a DTO comment.");
        commentDTO.setUserId(1L);
        commentDTO.setPostId(1L);
    }

    @Test
    void testGetCommentById() throws Exception {
        Long commentId = 1L;

        // Mock the service method
        when(commentService.getCommentById(commentId)).thenReturn(Optional.of(comment));

        // Perform GET request and verify the response
        mockMvc.perform(get("/api/comments/{commentId}", commentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(comment.getId()))
                .andExpect(jsonPath("$.content").value(comment.getContent()));
    }

    @Test
    void testCreateComment() throws Exception {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent("Test comment");
        commentDTO.setUserId(1L);
        commentDTO.setPostId(1L);

        Comment comment = new Comment();
        comment.setId(1L);
        comment.setContent("Test comment");

        when(commentService.createComment(any(Comment.class), eq(1L), eq(1L))).thenReturn(comment);

        mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"content\": \"Test comment\", \"userId\": 1, \"postId\": 1 }"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCommentsByPost() throws Exception {
        Long postId = 1L;

        // Mock the service method
        when(commentService.getAllCommentsByPost(postId)).thenReturn(Arrays.asList(comment));

        // Perform GET request and verify the response
        mockMvc.perform(get("/api/comments/post/{postId}", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(comment.getId()))
                .andExpect(jsonPath("$[0].content").value(comment.getContent()));
    }

    @Test
    void testUpdateComment() throws Exception {
        Long commentId = 1L;

        // Mock the service method
        when(commentService.updateComment(eq(commentId), any(Comment.class))).thenReturn(comment);

        // Convert Comment entity to JSON
        ObjectMapper mapper = new ObjectMapper();
        String commentJson = mapper.writeValueAsString(comment);

        // Perform PUT request and verify the response
        mockMvc.perform(put("/api/comments/{commentId}", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(commentJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(comment.getId()))
                .andExpect(jsonPath("$.content").value(comment.getContent()));
    }

    @Test
    void testDeleteComment() throws Exception {
        Long commentId = 1L;

        // Mock the service method
        when(commentService.deleteComment(commentId)).thenReturn("Comment deleted successfully.");

        // Perform DELETE request and verify the response
        mockMvc.perform(delete("/api/comments/{commentId}", commentId))
                .andExpect(status().isOk())
                .andExpect(content().string("Comment deleted successfully."));
    }
}
