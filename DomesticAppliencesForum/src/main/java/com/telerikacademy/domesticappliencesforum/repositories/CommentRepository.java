package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.FilterOptionsComment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    List<Comment> getAllComments(FilterOptionsComment filterOptionsComment);
    void create(Comment comment);
    void modify(Comment comment);
    void delete(int id);
    Comment getCommentById(int id);
}
