package com.socialmedia.controller;

import com.socialmedia.DTO.UserDTO;
import com.socialmedia.entity.User;
import com.socialmedia.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{userId}/follow/{followId}")
    public ResponseEntity<String> followUser(@PathVariable Long userId, @PathVariable Long followId) {
        followService.followUser(userId, followId);
        return ResponseEntity.ok("User followed successfully");
    }

    @PostMapping("/{userId}/unfollow/{followId}")
    public ResponseEntity<String> unfollowUser(@PathVariable Long userId, @PathVariable Long followId) {
        followService.unfollowUser(userId, followId);
        return ResponseEntity.ok("User unfollowed successfully");
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<Set<UserDTO>> getFollowers(@PathVariable Long userId) {
        Set<User> followers = followService.getFollowers(userId);
        Set<UserDTO> followerDTOs = followers.stream()
                .map(UserDTO::new) // Convert each User entity to a UserDTO
                .collect(Collectors.toSet());
        return ResponseEntity.ok(followerDTOs);
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<Set<UserDTO>> getFollowing(@PathVariable Long userId) {
        Set<User> following = followService.getFollowing(userId);
        Set<UserDTO> followingDTOs = following.stream()
                .map(UserDTO::new) // Convert each User entity to a UserDTO
                .collect(Collectors.toSet());
        return ResponseEntity.ok(followingDTOs);
    }

}

