package com.telerikacademy.domesticappliencesforum.mappers;

import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.dtos.CommentDto;
import com.telerikacademy.domesticappliencesforum.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    private UserService userService;

    @Autowired

    public CommentMapper(UserService userService) {
        this.userService = userService;
    }

    public Comment fromCommentDto(CommentDto commentDto){
        Comment comment=new Comment();
        comment.setContent(commentDto.getContent());
        comment.setCreatedByUser(commentDto.getCreatedBy());

        return comment;
    }
}
