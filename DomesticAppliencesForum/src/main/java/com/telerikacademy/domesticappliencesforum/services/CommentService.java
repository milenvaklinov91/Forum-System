package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.User;

import java.util.List;

public interface CommentService {

    List<Comment> getAllComments(String username);
    Comment browse(int id);
    void create(Comment comment, User user);
    void modify(Comment comment);
    void delete(int id);
    Comment getCommentById(int id);
}
