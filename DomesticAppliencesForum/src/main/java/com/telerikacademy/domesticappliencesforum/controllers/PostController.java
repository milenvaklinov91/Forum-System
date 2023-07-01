package com.telerikacademy.domesticappliencesforum.controllers;

import com.telerikacademy.domesticappliencesforum.exceptions.DuplicatePasswordException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityDuplicateException;
import com.telerikacademy.domesticappliencesforum.mappers.PostMapper;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.dtos.PostDto;
import com.telerikacademy.domesticappliencesforum.repositories.UserRepository;
import com.telerikacademy.domesticappliencesforum.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;

    private PostMapper postMapper;

    public PostController(PostService postService, UserRepository userRepository,PostMapper postMapper) {
        this.postService = postService;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
    }
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public Post browse(@PathVariable int id) {
        return postService.browse(id);
    }

    @PostMapping
    public Post create(@Valid @RequestBody PostDto postDto) {
        try {
            Post post = postMapper.fromPostDto(postDto);
            postService.
            return  post;
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Post modify(@Valid @RequestBody Post post) {
        try {
            postService.modify(post);
            return post;
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        postService.delete(id);
    }
}
