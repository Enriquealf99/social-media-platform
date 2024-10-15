package com.socialmedia.service;

import com.socialmedia.entity.Comment;
import com.socialmedia.entity.Post;
import com.socialmedia.entity.User;
import com.socialmedia.repository.CommentRepository;
import com.socialmedia.repository.PostRepository;
import com.socialmedia.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;


    public List<Comment> getAllCommentsByPost(Long postId){
        return commentRepository.findByPostId(postId);
    }

    public Comment createComment(Comment comment, Long userId, Long postId) {
        if (userId == null || postId == null) {
            throw new IllegalArgumentException("User ID and Post ID cannot be null.");
        }

        if (comment == null) {
            throw new IllegalArgumentException("Comment cannot be null.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        comment.setUser(user);
        comment.setPost(post);

        return commentRepository.save(comment);
    }


    public Optional<Comment> getCommentById(Long commentId){
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isEmpty()){
            throw new IllegalArgumentException("Comment does not exist.");
        }
        return comment;
    }

    public Comment updateComment(Long commentId, Comment comment){
        Comment oldcomment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        if (comment.getContent() != null){
            oldcomment.setContent(comment.getContent());
        }

        return commentRepository.save(oldcomment);
    }

    public String deleteComment(Long commentId){
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        commentRepository.deleteById(commentId);
        return "Comment deleted Successfully";
    }
}
