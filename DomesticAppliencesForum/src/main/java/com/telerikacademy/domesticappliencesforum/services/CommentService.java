package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.FilterOptionsComment;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentService {

    List<Comment> getAllComments(FilterOptionsComment filterOptionsComment);
    Comment browse(int id);
    void create(Comment comment, User user);
    void modify(Comment comment, User user);
    void delete(int id,User user);
}
