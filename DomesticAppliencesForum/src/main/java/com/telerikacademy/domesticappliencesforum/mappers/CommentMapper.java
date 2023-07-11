package com.telerikacademy.domesticappliencesforum.mappers;

import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.dtos.CommentDto;
import com.telerikacademy.domesticappliencesforum.services.CommentService;
import com.telerikacademy.domesticappliencesforum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommentMapper {

    private CommentService commentService;

    @Autowired
    public CommentMapper(CommentService commentService) {
        this.commentService = commentService;
    }

    public Comment fromCommentDto(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setComment(commentDto.getContent());
        return comment;
    }

    public Comment fromCommentDto(int id, CommentDto commentDto) {
        Comment comment = fromCommentDto(commentDto);
        comment.setCommentId(id);
        Comment repositoryComment = commentService.browse(id);
        comment.setCreatedByUser(repositoryComment.getCreatedByUser());
        return comment;
    }
}
