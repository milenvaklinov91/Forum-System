package com.telerikacademy.domesticappliencesforum.mappers;

import com.telerikacademy.domesticappliencesforum.models.dtos.CommentDto;
import com.telerikacademy.domesticappliencesforum.services.UserService;

import javax.xml.stream.events.Comment;

public class CommentMapper {

    private UserService userService;

    public CommentMapper(UserService userService) {
        this.userService = userService;
    }

    public Comment fromCommentDto(CommentDto commentDto){
     //   Comment comment=new Comment();
        return null;
    }
}
