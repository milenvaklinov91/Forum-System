package com.telerikacademy.domesticappliencesforum.controllers;

import com.telerikacademy.domesticappliencesforum.exceptions.*;
import com.telerikacademy.domesticappliencesforum.mappers.PostMapper;
import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.dtos.PostDto;
import com.telerikacademy.domesticappliencesforum.repositories.UserRepository;
import com.telerikacademy.domesticappliencesforum.services.PostService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;
    private PostMapper postMapper;
    private final AuthenticationHelper authenticationHelper;

    public PostController(PostService postService, UserRepository userRepository, PostMapper postMapper
            , AuthenticationHelper authenticationHelper) {
        this.postService = postService;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<Post> getAllPosts(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String localDate,
            @RequestParam(required = false) Integer lastTen,
            @RequestParam(required = false) Integer tagId
    ) {
        return postService.getAllPosts(username, localDate, lastTen,tagId);
    }

    @GetMapping("/{id}")
    public Post browse(@PathVariable int id) {
        return postService.browse(id);
    }

    @GetMapping("/{id}/all-comments")
    public List<Comment> getAllComments(@PathVariable int id) {
        return postService.getAllComments(id);
    }
    //TODO

    @PostMapping
    public Post create(@RequestHeader HttpHeaders headers, @Valid @RequestBody PostDto postDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Post post = postMapper.fromPostDto(postDto);
            postService.create(post, user);
            return post;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public Post modify(@RequestHeader HttpHeaders headers, @PathVariable int id, @Valid @RequestBody PostDto postDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Post post = postMapper.fromDto(id, postDto);
            postService.modify(post, user);
            return post;
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        postService.delete(id, user);
    }
}
