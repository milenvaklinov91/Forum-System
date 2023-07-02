package com.telerikacademy.domesticappliencesforum.controllers;

import com.telerikacademy.domesticappliencesforum.exceptions.DuplicatePasswordException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityDuplicateException;
import com.telerikacademy.domesticappliencesforum.exceptions.UnauthorizedOperationException;
import com.telerikacademy.domesticappliencesforum.mappers.PostMapper;
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
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;
    private PostMapper postMapper;
    private final AuthenticationHelper authenticationHelper;

    public PostController(PostService postService, UserRepository userRepository, PostMapper postMapper
            ,AuthenticationHelper authenticationHelper) {
        this.postService = postService;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<Post> getAllPosts(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) User authorId,
            @RequestParam(required = false) String localDate
            ) {
        return postService.getAllPosts(title,authorId,localDate);
    }

    @GetMapping("/{id}")
    public Post browse(@PathVariable int id) {
        return postService.browse(id);
    }

    @PostMapping
    public Post create(@RequestHeader HttpHeaders headers, @Valid @RequestBody PostDto postDto) {
        User user = authenticationHelper.tryGetUser(headers);
        Post post = postMapper.fromPostDto(postDto);
        postService.create(post);
        return post;

    }

    @PutMapping("/{id}")
    public Post modify(@RequestHeader HttpHeaders headers, @Valid @RequestBody PostDto postDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Post post = postMapper.fromPostDto(postDto);
            postService.modify(post, user);
            return post;
        }catch (UnauthorizedOperationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        postService.delete(id, user);
    }
}
