package com.socialmedia.controller;

import com.socialmedia.entity.Like;
import com.socialmedia.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikeController {


    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/post/{postId}")
    public List<Like> getLikesByPost(@PathVariable("postId") Long postId){
        return likeService.getAllLikesByPost(postId);
    }

    @GetMapping("/user/{userId}")
    public List<Like> getLikesByUser(@PathVariable("userId") Long userId){
        return likeService.getAllLikesByUser(userId);
    }

    @PostMapping("/post/{postId}/user/{userId}")
    public Like likePost(@PathVariable ("postId") Long postId, @PathVariable ("userId") Long userId){
        return likeService.likePost(postId, userId);
    }

    @DeleteMapping("/{likeId}")
    public String deleteLike(@PathVariable ("likeId") Long likeId){
        return likeService.deleteLike(likeId);
    }

}
