package com.socialmedia.service;

import com.socialmedia.entity.Post;
import com.socialmedia.entity.User;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public SearchService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    // Search for users by username
    public List<User> searchUsersByUsername(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }

    // Search for posts by content
    public List<Post> searchPostsByContent(String content) {
        return postRepository.findByContentContainingIgnoreCase(content);
    }

    // Search for posts by tags
    public List<Post> searchPostsByTags(String tags) {
        return postRepository.findByTagsContainingIgnoreCase(tags);
    }
}



