package com.telerikacademy.domesticappliencesforum.controllers;

import com.telerikacademy.domesticappliencesforum.mappers.CommentMapper;
import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.dtos.CommentDto;
import com.telerikacademy.domesticappliencesforum.repositories.UserRepository;
import com.telerikacademy.domesticappliencesforum.services.CommentService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;
    private final UserRepository userRepository;
    private final AuthenticationHelper authenticationHelper;

    private CommentMapper commentMapper;

    public CommentController(CommentService commentService, UserRepository userRepository, AuthenticationHelper authenticationHelper, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.userRepository = userRepository;
        this.authenticationHelper = authenticationHelper;
        this.commentMapper = commentMapper;
    }

    @GetMapping
    public List<Comment> getAllComments(
            @RequestParam(required = false) String username) {
        return commentService.getAllComments(username);
    }

    @GetMapping("/{id}")
    public Comment browse(@PathVariable int id) {
        return commentService.getCommentById(id);
    }

    @PostMapping
    public Comment create(@RequestHeader HttpHeaders headers, @Valid @RequestBody CommentDto commentDto) {
        User user = authenticationHelper.tryGetUser(headers);
        Comment comment = commentMapper.fromCommentDto(commentDto);
        commentService.create(comment,user);
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
