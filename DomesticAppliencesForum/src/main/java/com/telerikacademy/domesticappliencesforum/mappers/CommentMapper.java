package com.telerikacademy.domesticappliencesforum.mappers;

import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.dtos.CommentDto;
import com.telerikacademy.domesticappliencesforum.services.CommentService;
import com.telerikacademy.domesticappliencesforum.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    private CommentService commentService;
    private PostService postService;

    @Autowired
    public CommentMapper(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    public Comment fromCommentDto(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setComment(commentDto.getContent());
        comment.setPostId(postService.getById(commentDto.getPostId()));
        return comment;
    }

    public Comment fromCommentDtoWithID(int id, CommentDto commentDto) {
        Comment comment = fromCommentDto(commentDto);
        comment.setCommentId(id);
        Comment repositoryComment = commentService.browse(id);
        comment.setCreatedByUser(repositoryComment.getCreatedByUser());
        return comment;
    }
}
