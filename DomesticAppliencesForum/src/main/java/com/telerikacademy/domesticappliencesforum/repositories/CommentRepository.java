package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.Post;

import java.util.List;

public interface CommentRepository {

    List<Comment> getAllComments();
    Comment browse(int id);
    void create(Comment comment);
    void modify(Comment comment);
    void delete(int id);
    Comment getCommentById(int id);
}
