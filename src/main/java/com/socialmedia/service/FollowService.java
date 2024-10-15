package com.socialmedia.service;

import com.socialmedia.entity.User;
import com.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowService {

    private final UserRepository userRepository;

    /**
     * Follows a user.
     * @param followerId The ID of the follower.
     * @param followId The ID of the user to be followed.
     */
    public void followUser(Long followerId, Long followId) {
        User follower = validateUserExists(followerId, "Follower user not found");
        User follow = validateUserExists(followId, "User to be followed not found");

        if (!follower.getFollowing().contains(follow)) {
            follower.addFollowing(follow);  // Add following relationship
            follow.addFollower(follower);  // Add follower relationship
        }

        // Save only the follower, as the cascading will save the related entities
        userRepository.save(follower);
    }

    /**
     * Unfollows a user.
     * @param followerId The ID of the follower.
     * @param followeeId The ID of the user to be unfollowed.
     */
    public void unfollowUser(Long followerId, Long followeeId) {
        User follower = validateUserExists(followerId, "Follower user not found");
        User follow = validateUserExists(followeeId, "User to be unfollowed not found");

        if (follower.getFollowing().contains(follow)) {
            follower.removeFollowing(follow);  // Remove following relationship
            follow.removeFollower(follower);  // Remove follower relationship
        }

        // Save only the follower, as the cascading will save the related entities
        userRepository.save(follower);
    }

    /**
     * Gets the followers of a user.
     * @param userId The ID of the user.
     * @return The set of followers.
     */
    public Set<User> getFollowers(Long userId) {
        User user = validateUserExists(userId, "User not found");
        return user.getFollowers();
    }

    /**
     * Gets the users followed by a user.
     * @param userId The ID of the user.
     * @return The set of users being followed.
     */
    public Set<User> getFollowing(Long userId) {
        User user = validateUserExists(userId, "User not found");
        return user.getFollowing();
    }

    /**
     * Validates if a user exists with the given ID.
     * @param userId The ID of the user.
     * @param errorMessage The error message to throw if the user is not found.
     * @return The found user entity.
     */
    private User validateUserExists(Long userId, String errorMessage) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException(errorMessage));
    }
}

