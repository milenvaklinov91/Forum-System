package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.exceptions.UnauthorizedOperationException;
import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.FilterOptionsComment;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.CommentRepository;
import com.telerikacademy.domesticappliencesforum.services.interfaces.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {


    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getAllComments(FilterOptionsComment filterOptionsComment) {
        return commentRepository.getAllComments(filterOptionsComment);
    }

    @Override
    public Comment browse(int id) {
        return commentRepository.getCommentById(id);
    }

    @Override
    public void create(Comment comment, User user) {
        comment.setCreatedByUser(user);
        comment.setCreateTime(LocalDateTime.now());
        commentRepository.create(comment);
    }

    @Override
    public void modify(Comment comment, User user) {
        if (!(comment.getCreatedByUser().getUsername().equals(user.getUsername()))) {
            throw new UnauthorizedOperationException("You're not authorized to perform this operation as your are not the owner of this comment");
        }
        commentRepository.modify(comment);
    }

    @Override
    public void delete(int id, User user) {
        Comment comment = commentRepository.getCommentById(id);
        if (!user.isAdmin() || !comment.getCreatedByUser().getUsername().equals(user.getUsername())) {
            throw new UnauthorizedOperationException("Only admins and comment owners are authorized to delete this comment");
        }
        commentRepository.delete(id);
    }

    @Override
    public Comment getCommentById(int id) {
        return commentRepository.getCommentById(id);
    }
}
