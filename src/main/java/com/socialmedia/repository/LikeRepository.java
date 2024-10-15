package com.socialmedia.repository;

import com.socialmedia.entity.Like;
import com.socialmedia.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Post getPostById(Long postId);

    List<Like> findAllLikesByPostId(Long postId);

    List<Like> findAllLikesByUserId(Long userId);
}
