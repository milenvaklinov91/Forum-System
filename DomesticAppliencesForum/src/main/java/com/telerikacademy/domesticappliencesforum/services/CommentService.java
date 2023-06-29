package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.models.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getAllComments();
    Comment browse(int id);
    void create(Comment comment);
    void modify(Comment comment);
    void delete(int id);
    Comment getCommentById(int id);
}
