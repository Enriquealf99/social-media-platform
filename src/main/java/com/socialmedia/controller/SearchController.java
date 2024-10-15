package com.socialmedia.controller;

import com.socialmedia.entity.Post;
import com.socialmedia.entity.User;
import com.socialmedia.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class  SearchController {

    private final SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    // Endpoint to search for users by username
    @GetMapping("/users")
    public List<User> searchUsers(@RequestParam String username) {
        return searchService.searchUsersByUsername(username);
    }

    // Endpoint to search for posts by content
    @GetMapping("/posts/content")
    public List<Post> searchPostsByContent(@RequestParam String content) {
        return searchService.searchPostsByContent(content);
    }

    // Endpoint to search for posts by tags
    @GetMapping("/posts/tags")
    public List<Post> searchPostsByTags(@RequestParam String tags) {
        return searchService.searchPostsByTags(tags);
    }
}

