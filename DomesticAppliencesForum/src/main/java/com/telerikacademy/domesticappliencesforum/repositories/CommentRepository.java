package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;

import java.util.List;

public interface CommentRepository {

    List<Comment> getAllComments(String username);
    Comment browse(int id);
    void create(Comment comment, User user);
    void modify(Comment comment);
    void delete(int id);
    Comment getCommentById(int id);
}
