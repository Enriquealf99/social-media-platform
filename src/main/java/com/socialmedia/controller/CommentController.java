package com.socialmedia.controller;

import com.socialmedia.DTO.CommentDTO;
import com.socialmedia.entity.Comment;
import com.socialmedia.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private final CommentService commentService;

    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }


    @GetMapping("/{commentId}")
    public Optional<Comment> getComment(@PathVariable("commentId") Long commentId){
        return commentService.getCommentById(commentId);
    }

    @PostMapping
    public Comment createComment(
            @RequestBody CommentDTO commentDTO
    ) {
        // Convert DTO to Comment entity
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());

        return commentService.createComment(comment, commentDTO.getUserId(), commentDTO.getPostId());
    }


    @GetMapping("/post/{postId}")
    public List<Comment> getCommentsByPost(@PathVariable("postId") Long postId){
        return commentService.getAllCommentsByPost(postId);
    }

    @PutMapping("/{commentId}")
    public Comment updateComment(@RequestBody Comment comment, @PathVariable("commentId") Long commentId){
        return commentService.updateComment(commentId, comment);
    }

    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable ("commentId") Long commentId){
        return commentService.deleteComment(commentId);
    }

}
