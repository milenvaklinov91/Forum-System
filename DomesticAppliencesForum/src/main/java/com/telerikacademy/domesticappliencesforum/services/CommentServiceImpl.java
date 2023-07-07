package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.exceptions.UnauthorizedOperationException;
import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public List<Comment> getAllComments(String username, String localDate, Integer vote) {
        return commentRepository.getAllComments(username,localDate,vote);
    }

    @Override
    public Comment browse(int id) {

        return commentRepository.browse(id);
    }

    @Override
    public void create(Comment comment, User user) {
        commentRepository.create(comment, user);
    }

    @Override
    public void modify(Comment comment, User user) {
        if (!(comment.getCreatedByUser().getUsername().equals(user.getUsername()))) {
            throw new UnauthorizedOperationException("You're not authorized for this operation");
        }
        commentRepository.modify(comment);
    }

    @Override
    public void delete(int id, User user) {
        Comment comment = commentRepository.getCommentById(id);
        if (!user.isAdmin() || !comment.getCreatedByUser().getUsername().equals(user.getUsername())) {
            throw new UnauthorizedOperationException("Only admins can delete");
        }
        //TODO Admina ни не работи както трябва!!!
        commentRepository.delete(id);
    }

    @Override
    public Comment getCommentById(int id) {
        return commentRepository.getCommentById(id);
    }
}
