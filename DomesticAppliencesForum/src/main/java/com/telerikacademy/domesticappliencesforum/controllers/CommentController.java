package com.telerikacademy.domesticappliencesforum.controllers;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityDuplicateException;
import com.telerikacademy.domesticappliencesforum.mappers.CommentMapper;
import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.dtos.CommentDto;
import com.telerikacademy.domesticappliencesforum.repositories.UserRepository;
import com.telerikacademy.domesticappliencesforum.services.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;
    private final UserRepository userRepository;

    private CommentMapper commentMapper;

    public CommentController(CommentService commentService, UserRepository userRepository, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.userRepository = userRepository;
        this.commentMapper = commentMapper;
    }

    @GetMapping
    public List<Comment> getAllComments() {
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    public Comment browse(@PathVariable int id) {
        return commentService.getCommentById(id);
    }

    @PostMapping
    public Comment create(@Valid @RequestBody CommentDto commentDto) {
        Comment comment = commentMapper.fromCommentDto(commentDto);
        commentService.create(comment);
        return comment;
    }
    @PutMapping("/{id}")
    public Comment modify(@Valid @RequestBody CommentDto commentDto){
        Comment comment = commentMapper.fromCommentDto(commentDto);
        commentService.modify(comment);
        return comment;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        commentService.delete(id);
    }

}
