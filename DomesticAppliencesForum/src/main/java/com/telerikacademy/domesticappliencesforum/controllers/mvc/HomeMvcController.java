package com.telerikacademy.domesticappliencesforum.controllers.mvc;

import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.PostFilterOptions;
import com.telerikacademy.domesticappliencesforum.services.interfaces.PostService;
import com.telerikacademy.domesticappliencesforum.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeMvcController {
    private final PostService postService;

    private final UserService userService;

    @Autowired
    public HomeMvcController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping
    public String showHomePage(Model model) {
        PostFilterOptions topCommentedOption = new PostFilterOptions();
        topCommentedOption.getMostComments();
        List<Post> topCommentedPosts = postService.getAllPosts(topCommentedOption);

        PostFilterOptions latestPostsOption = new PostFilterOptions();
        latestPostsOption.getLastTen();
        List<Post> latestPosts = postService.getAllPosts(latestPostsOption);

        PostFilterOptions mostLikedPostsOption = new PostFilterOptions();
        mostLikedPostsOption.getTopLiked();
        List<Post> mostLikedPost = postService.getAllPosts(mostLikedPostsOption);


        Long numberOfUsers = userService.countAllUsers();
        Long numberOfPosts = postService.countAllPosts();

        model.addAttribute("topCommentedPosts", topCommentedPosts);
        model.addAttribute("latestPosts", latestPosts);
        model.addAttribute("mostLikedPost",mostLikedPost);
        model.addAttribute("numberOfUsers", numberOfUsers);
        model.addAttribute("numberOfPosts", numberOfPosts);

        return "homePage";
    }
}

