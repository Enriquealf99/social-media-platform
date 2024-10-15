package com.socialmedia.repository;

import com.socialmedia.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);

    List<Post> findByTagsContainingIgnoreCase(String tags);

    List<Post> findByContentContainingIgnoreCase(String content);
}
