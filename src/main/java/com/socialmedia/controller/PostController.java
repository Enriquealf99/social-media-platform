package com.socialmedia.controller;

import com.socialmedia.DTO.PostUpdateDTO;
import com.socialmedia.entity.Post;
import com.socialmedia.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create-post")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @RequestParam Long userid){
        Post createdPost = postService.createPost(post, userid);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/all-posts")
    public ResponseEntity<List<Post>> getAllPosts(){
        List<Post> posts = postService.getAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<Optional<Post>> getPostById(@PathVariable("post_id") Long post_id){
        Optional<Post> post = postService.findPostById(post_id);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PutMapping("/{post_id}")
    public ResponseEntity<Post> updatePost(@PathVariable("post_id") Long post_id, @RequestBody PostUpdateDTO updatedPost){
        Post post = postService.updatePost(post_id, updatedPost);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @DeleteMapping("/{post_id}")
    public ResponseEntity<Void> deletePost(@PathVariable("post_id") Long post_id) {
        postService.deletePost(post_id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<Post>> getPostsByUser(@PathVariable ("user_id") Long user_id){
        List<Post> posts = postService.findAllPostsByUserId(user_id);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
