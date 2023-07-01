package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository

public class CommentRepositoryImpl implements CommentRepository{

    private List<Comment> comments;

    private int commentId;
    @Autowired
    public CommentRepositoryImpl() {
        comments = new ArrayList<>();

        Comment comment1 = new Comment("Content1", 1);
        comment1.setCommentId(++commentId);
        comments.add(comment1);

        Comment comment2 = new Comment("Content2", 2);
        comment2.setCommentId(++commentId);
        comments.add(comment2);
    }

    @Override
    public List<Comment> getAllComments() {
        return new ArrayList<>(comments);
    }

    @Override
    public Comment browse(int id) {
        return null;
    }

    @Override
    public void create(Comment comment) {
        comment.setCommentId(++commentId);
        comments.add(comment);
    }

    @Override
    public void modify(Comment comment) {
        Comment commentToUpdate = getCommentById(comment.getCommentId());
        commentToUpdate.setContent(comment.getContent());
    }

    @Override
    public void delete(int id) {
        Comment commentToDelete=getCommentById(id);
        comments.remove(commentToDelete);

    }

    @Override
    public Comment getCommentById(int id) {

        return comments.stream()
                .filter(post -> post.getCommentId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Comment", id));
    }
}
