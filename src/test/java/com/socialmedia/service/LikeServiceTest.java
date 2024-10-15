package com.socialmedia.service;

import com.socialmedia.entity.Like;
import com.socialmedia.entity.Post;
import com.socialmedia.entity.User;
import com.socialmedia.repository.LikeRepository;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LikeServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LikeService likeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLikePost_successful() {
        // Arrange - Set up mock data
        Long postId = 1L;
        Long userId = 1L;

        Post post = new Post();
        post.setId(postId);

        User user = new User();
        user.setId(userId);

        Like expectedLike = new Like();
        expectedLike.setId(1L);
        expectedLike.setPost(post);
        expectedLike.setUser(user);

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(likeRepository.save(any(Like.class))).thenReturn(expectedLike);

        // Act - Call the service method
        Like actualLike = likeService.likePost(postId, userId);

        // Assert - Verify the result
        assertEquals(expectedLike.getId(), actualLike.getId());
        verify(postRepository, times(1)).findById(postId);
        verify(userRepository, times(1)).findById(userId);
        verify(likeRepository, times(1)).save(any(Like.class));
    }

    @Test
    void testLikePost_postNotFound() {
        // Arrange
        Long postId = 1L;
        Long userId = 1L;

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // Act & Assert
        try {
            likeService.likePost(postId, userId);
        } catch (IllegalArgumentException e) {
            assertEquals("Post not found with ID: " + postId, e.getMessage());
        }
        verify(postRepository, times(1)).findById(postId);
    }
}

