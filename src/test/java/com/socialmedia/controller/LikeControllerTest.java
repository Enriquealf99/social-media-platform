package com.socialmedia.controller;

import com.socialmedia.entity.Like;
import com.socialmedia.service.LikeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LikeController.class)
class LikeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LikeService likeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetLikesByPost() throws Exception {
        Long postId = 1L;

        Like like = new Like();
        like.setId(1L);
        when(likeService.getAllLikesByPost(postId)).thenReturn(Collections.singletonList(like));

        mockMvc.perform(get("/api/likes/post/{postId}", postId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    void testLikePost() throws Exception {
        Long postId = 1L;
        Long userId = 1L;

        Like like = new Like();
        like.setId(1L);
        when(likeService.likePost(postId, userId)).thenReturn(like);

        mockMvc.perform(post("/api/likes/post/{postId}/user/{userId}", postId, userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }
}

