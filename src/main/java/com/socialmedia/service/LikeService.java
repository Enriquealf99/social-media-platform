package com.socialmedia.service;

import com.socialmedia.entity.Like;
import com.socialmedia.entity.Post;
import com.socialmedia.entity.User;
import com.socialmedia.repository.CommentRepository;
import com.socialmedia.repository.LikeRepository;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public LikeService(LikeRepository likeRepository, PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.likeRepository = likeRepository;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public Like likePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        // Check if the user already liked the post (optional)
        Like like = new Like();
        like.setPost(post);
        like.setUser(user);

        return likeRepository.save(like);
    }

    public String deleteLike(Long likeId){
        Like like = likeRepository.findById(likeId)
                .orElseThrow(() -> new IllegalArgumentException("Like didn't exist"));

        likeRepository.deleteById(likeId);
        return "Like deleted successfully.";
    }

    public List<Like> getAllLikesByPost(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found with ID: " + postId));

        return likeRepository.findAllLikesByPostId(postId);
    }

    public List<Like> getAllLikesByUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));

        return likeRepository.findAllLikesByUserId(userId);
    }
}
