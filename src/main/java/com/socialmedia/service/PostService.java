package com.socialmedia.service;

import com.socialmedia.DTO.PostUpdateDTO;
import com.socialmedia.entity.Post;
import com.socialmedia.entity.User;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }

    public Optional<Post> findPostById(Long id){
        return postRepository.findById(id);
    }

    public void deletePost(Long id){
        Optional<Post> post = postRepository.findById(id);
        if(post.isEmpty()){
            throw new IllegalArgumentException("Post does not exist");
        }
        postRepository.deleteById(id);
    }

    public Post createPost(Post post, Long userId) {
        // Fetch the user from the database by userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Set the user for the post
        post.setUser(user);

        // Save the post
        return postRepository.save(post);
    }

    public Post updatePost(Long postId, PostUpdateDTO updatedPost) {
        // Fetch the post by ID
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (updatedPost.getTitle() != null) {
            existingPost.setTitle(updatedPost.getTitle());
        }
        if (updatedPost.getContent() != null) {
            existingPost.setContent(updatedPost.getContent());
        }
        if (updatedPost.getTags() != null) {
            existingPost.setTags(updatedPost.getTags());
        }

        return postRepository.save(existingPost);
    }


    public List<Post> findAllPostsByUserId(Long userId) {
        return postRepository.findByUserId(userId);
    }

}
