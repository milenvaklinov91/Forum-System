package com.telerikacademy.domesticappliencesforum.mappers;

import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.dtos.CommentDto;
import com.telerikacademy.domesticappliencesforum.services.interfaces.CommentService;
import com.telerikacademy.domesticappliencesforum.services.interfaces.PostService;
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
        comment.setComment(commentDto.getComment());
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
    public CommentDto toDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setComment(comment.getComment());
        commentDto.setPostId(comment.getPostId().getPostId());
        return commentDto;
    }
}
