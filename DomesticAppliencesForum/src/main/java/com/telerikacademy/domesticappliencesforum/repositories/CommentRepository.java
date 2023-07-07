package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.User;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository {

    List<Comment> getAllComments(String username, String localDate, Integer vote);
    Comment browse(int id);
    void create(Comment comment, User user);
    void modify(Comment comment);
    void delete(int id);
    Comment getCommentById(int id);
}
