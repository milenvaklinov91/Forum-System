package com.telerikacademy.domesticappliencesforum.controllers.mvc;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.mappers.PostMapper;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.TagTypes;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.Vote;
import com.telerikacademy.domesticappliencesforum.models.dtos.PostDto;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.PostFilterOptions;
import com.telerikacademy.domesticappliencesforum.repositories.PostRepositoryImpl;
import com.telerikacademy.domesticappliencesforum.repositories.VoteRepositoryImpl;
import com.telerikacademy.domesticappliencesforum.services.interfaces.PostService;
import com.telerikacademy.domesticappliencesforum.services.interfaces.TagTypesService;
import com.telerikacademy.domesticappliencesforum.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostMvcController {
    private final PostService postService;
    private final UserService userService;
    private final TagTypesService tagTypesService;
    private final PostMapper modelMapper;

    private final PostRepositoryImpl postRepository;

    private final VoteRepositoryImpl voteRepository;

    @Autowired
    public PostMvcController(PostService postService,
                             UserService userService,
                             TagTypesService tagTypesService,
                             PostMapper modelMapper, PostRepositoryImpl postRepository, VoteRepositoryImpl voteRepository) {
        this.postService = postService;
        this.userService = userService;
        this.tagTypesService = tagTypesService;
        this.modelMapper = modelMapper;

        this.postRepository = postRepository;
        this.voteRepository = voteRepository;
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
            int likes=postRepository.getPostLikes(id);
            int disLikes=postRepository.getPostDisLikes(id);
            model.addAttribute("post", post);
            model.addAttribute("likes",likes);
            model.addAttribute("disLikes",disLikes);
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
    public String createPost(@Valid @ModelAttribute("post") PostDto post, BindingResult errors, Model model, HttpSession session) {
        if (errors.hasErrors()) {
            return "post-new";
        } else if (post.getTagTypeID() == 0) {
            session.setAttribute("currentPost", post);
            return "redirect:/tags/new";
        } else {
                User creator = userService.getById(2);
                Post newPost = modelMapper.fromPostDto(post);
                postService.create(newPost, creator);
                return "redirect:/posts";
        }
    }

    @GetMapping("/{id}/update")
    public String showEditPostPage(@PathVariable int id, Model model) {
        try {
            Post post = postService.getById(id);
            PostDto postDto = modelMapper.toDto(post);
            model.addAttribute("postId", id);
            model.addAttribute("post", postDto);
            return "post-update";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }
    }
    @PostMapping("/{id}/update")
    public String updatePost(@PathVariable int id,@Valid @ModelAttribute("post") PostDto post, BindingResult errors, Model model, HttpSession session) {
        if (errors.hasErrors()) {
            return "post-update";
        } else {
            User user = userService.getById(2);
            Post newPost = modelMapper.fromDto(id,post);
            postService.modify(newPost, user);
            return "redirect:/posts";
        }
    }

    @GetMapping("/{id}/delete")
    public String deletePost(@PathVariable int id, Model model) {
        try {
            User user = userService.getById(2);
            postService.delete(id, user);
            return "redirect:/posts";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }
    }

    @GetMapping("/{id}/seeVotes")
    public String seePostVotes(@PathVariable int id, Model model) {
        try {
            List<Vote> votes = voteRepository.getVotesByPostId(id);
            Post post=postService.getById(id);

            int likes = 0;
            int dislikes = 0;
            List<String> usersWhoLiked = new ArrayList<>();
            List<String> usersWhoDisliked = new ArrayList<>();

            for (Vote vote : votes) {
                if (vote.getType().getVoteTypeID() == 1) {
                    likes++;
                    usersWhoLiked.add(vote.getCreatedBy().getUsername());
                } else if (vote.getType().getVoteTypeID() == 2) {
                    dislikes++;
                    usersWhoDisliked.add(vote.getCreatedBy().getUsername());
                }
            }

            model.addAttribute("postId", id);
            model.addAttribute("post", post);
            model.addAttribute("likes", likes);
            model.addAttribute("dislikes", dislikes);
            model.addAttribute("usersWhoLiked", usersWhoLiked);
            model.addAttribute("usersWhoDisliked", usersWhoDisliked);
        } catch (EntityNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "vote";
    }


}
