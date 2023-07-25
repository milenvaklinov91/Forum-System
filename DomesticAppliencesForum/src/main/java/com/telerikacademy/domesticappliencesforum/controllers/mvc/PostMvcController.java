package com.telerikacademy.domesticappliencesforum.controllers.mvc;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.mappers.PostMapper;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.TagTypes;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.dtos.PostDto;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.PostFilterOptions;
import com.telerikacademy.domesticappliencesforum.services.interfaces.PostService;
import com.telerikacademy.domesticappliencesforum.services.interfaces.TagTypesService;
import com.telerikacademy.domesticappliencesforum.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostMvcController {
    private final PostService postService;
    private final UserService userService;
    private final TagTypesService tagTypesService;
    private final PostMapper modelMapper;

    @Autowired
    public PostMvcController(PostService postService,
                             UserService userService,
                             TagTypesService tagTypesService,
                             PostMapper modelMapper) {
        this.postService = postService;
        this.userService = userService;
        this.tagTypesService = tagTypesService;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("tags")
    public List<TagTypes> populateStyles() {
        return tagTypesService.get();
    }

    @GetMapping
    public String showAllPosts(Model model) {
        List<Post> posts = postService.getAllPosts(new PostFilterOptions());
        model.addAttribute("posts", posts);
        return "posts";
    }

    @GetMapping("/{id}")
    public String showSinglePost(@PathVariable int id, Model model) {
        try {
            Post post = postService.getById(id);
            model.addAttribute("post", post);
            return "post";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }

    }

    @GetMapping("/new")
    public String showNewPostPage(Model model) {
        model.addAttribute("post", new PostDto());
        return "post-new";
    }

    @PostMapping("/new")
    public String createPost(@ModelAttribute PostDto post) {
        User creator=userService.getById(2);
        Post newPost=modelMapper.fromPostDto(post);
        postService.create(newPost,creator);
        return "redirect:/posts";
    }
}
