package com.telerikacademy.domesticappliencesforum.controllers.rest;

import com.telerikacademy.domesticappliencesforum.controllers.AuthenticationHelper;
import com.telerikacademy.domesticappliencesforum.exceptions.AuthorizationException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.exceptions.UnauthorizedOperationException;
import com.telerikacademy.domesticappliencesforum.mappers.CommentMapper;
import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.dtos.CommentDto;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.FilterOptionsComment;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.UserRepository;
import com.telerikacademy.domesticappliencesforum.services.interfaces.CommentService;
import org.springframework.http.HttpHeaders;
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
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String localDate,
            @RequestParam(required = false) Integer vote,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder
            ) {
        FilterOptionsComment filterOptionsComment = new FilterOptionsComment(userId, localDate, vote, sortBy, sortOrder);
        return commentService.getAllComments(filterOptionsComment);
    }

    @GetMapping("/{id}")
    public Comment browse(@PathVariable int id) {
        return commentService.browse(id);
    }

    @PostMapping
    public Comment create(@RequestHeader HttpHeaders headers, @Valid @RequestBody CommentDto commentDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Comment comment = commentMapper.fromCommentDto(commentDto);
            commentService.create(comment, user);
            return comment;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Comment modify(@RequestHeader HttpHeaders headers, @PathVariable int id, @Valid @RequestBody CommentDto commentDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Comment comment = commentMapper.fromCommentDtoWithID(id, commentDto);
            commentService.modify(comment, user);
            return comment;
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        commentService.delete(id, user);
    }

}