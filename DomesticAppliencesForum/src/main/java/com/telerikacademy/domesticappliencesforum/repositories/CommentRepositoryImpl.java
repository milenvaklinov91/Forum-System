package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository

public class CommentRepositoryImpl implements CommentRepository {

    private List<Comment> comments;

    private int commentId;

    @Autowired
    public CommentRepositoryImpl(UserRepository userRepository) {
        /*comments = new ArrayList<>();

        Comment comment1 = new Comment("Content1", userRepository.getUserById(1));
        comment1.setCommentId(++commentId);
        comments.add(comment1);

        Comment comment2 = new Comment("Content2", userRepository.getUserById(2));
        comment2.setCommentId(++commentId);
        comments.add(comment2);*/
    }

    @Override
    public List<Comment> getAllComments(String username) {
        List<Comment> result = new ArrayList<>(comments);
        result = filterByAuthor(result, username);
        return result;
    }


    @Override
    public Comment browse(int id) {
        return null;
    }

    @Override
    public void create(Comment comment, User user) {
        comment.setCommentId(++commentId);
        comments.add(comment);
    }

    @Override
    public void modify(Comment comment) {
        Comment commentToUpdate = getCommentById(comment.getCommentId());
        commentToUpdate.setComment(comment.getComment());
    }

    @Override
    public void delete(int id) {
        Comment commentToDelete = getCommentById(id);
        comments.remove(commentToDelete);

    }

    @Override
    public Comment getCommentById(int id) {

        return comments.stream()
                .filter(post -> post.getCommentId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Comment", id));
    }

    private List<Comment> filterByAuthor(List<Comment> comments, String username) {
        if (comments != null && username != null) {
            comments = comments.stream()
                    .filter(comment -> comment.getCreatedByUser().getUsername().equals(username))
                    .collect(Collectors.toList());
        }
        return comments;
    }
}
