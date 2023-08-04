package com.telerikacademy.domesticappliencesforum.controllers.mvc;

import com.telerikacademy.domesticappliencesforum.controllers.AuthenticationHelper;
import com.telerikacademy.domesticappliencesforum.exceptions.AuthorizationException;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.dtos.PostFilterDto;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.PostFilterOptions;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.PostRepository;
import com.telerikacademy.domesticappliencesforum.services.interfaces.PostService;
import com.telerikacademy.domesticappliencesforum.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeMvcController {
    private final PostService postService;

    private final PostRepository postRepository;
    private final UserService userService;
    AuthenticationHelper authenticationHelper;

    @Autowired
    public HomeMvcController(PostService postService, PostRepository postRepository, UserService userService,
                             AuthenticationHelper authenticationHelper) {
        this.postService = postService;
        this.postRepository = postRepository;
        this.userService = userService;
        this.authenticationHelper= authenticationHelper;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @ModelAttribute("isAdmin")
    public boolean populateIsAdmin(HttpSession session) {
        try {
            User currentUser = authenticationHelper.tryGetCurrentUser(session);
            return currentUser.isAdmin();
        } catch (AuthorizationException e) {
            return false;
        }
    }

    @GetMapping
    public String showHomePage(@ModelAttribute("filter") PostFilterDto filter,Model model) {

        List<Post> topCommentedPosts = postRepository.getMostCommented();

        List<Post> latestPosts = postRepository.getLastTenCreatedPosts();

        List<Post> mostLikedPost = postRepository.getMostLiked();


        Long numberOfUsers = userService.countAllUsers();
        Long numberOfPosts = postService.countAllPosts();

        model.addAttribute("topCommentedPosts", topCommentedPosts);
        model.addAttribute("latestPosts", latestPosts);
        model.addAttribute("mostLikedPost", mostLikedPost);
        model.addAttribute("numberOfUsers", numberOfUsers);
        model.addAttribute("numberOfPosts", numberOfPosts);

        return "homePage";
    }

    @GetMapping("/admin")
    public String showAdminPortal(HttpSession session, Model model) {
        try {
            User user = authenticationHelper.tryGetCurrentUser(session);
            if(!user.isAdmin()) {
                model.addAttribute("error", "You don't have access to the requested resource.");
                return "AccessDeniedView";
            }
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        }

        return "AdminPortalView";
    }
}

