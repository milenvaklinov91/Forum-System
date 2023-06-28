package com.telerikacademy.domesticappliencesforum.controllers;

import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.repositories.PostRepository;
import com.telerikacademy.domesticappliencesforum.repositories.UserRepository;
import com.telerikacademy.domesticappliencesforum.services.PostService;
import com.telerikacademy.domesticappliencesforum.services.PostServiceImpl;
import com.telerikacademy.domesticappliencesforum.services.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;

    public PostController(PostService postService, UserRepository userRepository) {
        this.postService = postService;
        this.userRepository=userRepository;
    }
    @GetMapping("/{id}")
    public Post browse(@PathVariable int id){
        return postService.browse(id);
        //TODO По този начин показва много излишни неща относно потребителя,
        // който е създал поста. Трябва да направим DTO класове
    }

    @PostMapping
    public Post create(@RequestBody Post post){
        return post;
        //TODO Не работи!!!!
    }
    @PutMapping("/{id}")
    public void modify(@Valid @RequestBody Post post){
        postService.modify(post);
        //TODO Не работи!!!!
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id){
        postService.delete(id);
    }



}
