package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {


    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.getAllComments();
    }

    @Override
    public Comment browse(int id) {

        return commentRepository.browse(id);
    }

    @Override
    public void create(Comment comment) {
        commentRepository.create(comment);
    }

    @Override
    public void modify(Comment comment) {
        commentRepository.modify(comment);
    }

    @Override
    public void delete(int id) {
        commentRepository.delete(id);
    }

    @Override
    public Comment getCommentById(int id) {
        return commentRepository.getCommentById(id);
    }
}
