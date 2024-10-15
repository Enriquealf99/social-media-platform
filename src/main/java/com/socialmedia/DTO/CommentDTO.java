package com.socialmedia.DTO;

import com.socialmedia.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDTO {
    private Long id;
    private String content;
    private Long userId;
    private Long postId;

    public static CommentDTO fromEntity(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setContent(comment.getContent());
        dto.setUserId(comment.getUser().getId());
        dto.setPostId(comment.getPost().getId());
        return dto;
    }

    // Convert from DTO to Comment entity
    public Comment toEntity() {
        Comment comment = new Comment();
        comment.setContent(this.content);
        return comment;
    }

    // Getters and setters
}

