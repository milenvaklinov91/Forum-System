package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.User;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentService {

    List<Comment> getAllComments(String username, String localDate, Integer vote);
    Comment browse(int id);
    void create(Comment comment, User user);
    void modify(Comment comment,User user);
    void delete(int id,User user);
    Comment getCommentById(int id);
}
