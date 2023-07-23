package com.telerikacademy.domesticappliencesforum.controllers;

import com.telerikacademy.domesticappliencesforum.exceptions.*;
import com.telerikacademy.domesticappliencesforum.mappers.PostMapper;
import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.dtos.PostDto;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.PostFilterOptions;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.UserRepository;
import com.telerikacademy.domesticappliencesforum.services.interfaces.PostService;
import org.springframework.http.HttpHeaders;
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
            @RequestParam(required = false) Integer tagId,
            @RequestParam(required = false) String mostComments,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortOrder
    ) {
        PostFilterOptions filterOptions = new PostFilterOptions(username, localDate,
                lastTen, tagId, mostComments, sortBy, sortOrder);
        return postService.getAllPosts(filterOptions);
    }

    @GetMapping("/count")
    public Long countAllPosts() {
        return postService.countAllPosts();
    }

    @GetMapping("/{id}")
    public Post getById(@PathVariable int id) {
        return postService.getById(id);
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getAllComments(@PathVariable int id) {
        return postService.getAllComments(id);
    }

    @GetMapping("/{id}/likes")
    public int getLikedPost(@PathVariable int id) {
        return postService.getPostLikes(id);
    }

    @GetMapping("/{id}/dislikes")
    public int getDisLikedPost(@PathVariable int id) {
        return postService.getPostDisLikes(id);
    }


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
