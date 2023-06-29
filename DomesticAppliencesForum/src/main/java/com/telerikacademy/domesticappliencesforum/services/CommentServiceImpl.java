package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CommentServiceImpl implements CommentService{


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
        return null;
    }

    @Override
    public void create(Comment comment) {

    }

    @Override
    public void modify(Comment comment) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public Comment getCommentById(int id) {
        return commentRepository.getCommentById(id);
    }
}
