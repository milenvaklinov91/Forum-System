package com.telerikacademy.domesticappliencesforum.repositories.interfaces;

import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.FilterOptionsComment;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository {

    List<Comment> getAllComments(FilterOptionsComment filterOptionsComment);
    Long countAllComments();

    void create(Comment comment);

    void modify(Comment comment);

    void delete(int id);

    Comment getCommentById(int id);

    int getCommentLikes(int commentId);
    int getCommentDisLikes(int commentId);
}
