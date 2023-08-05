package com.telerikacademy.domesticappliencesforum.controllers.mvc;

import com.telerikacademy.domesticappliencesforum.controllers.AuthenticationHelper;
import com.telerikacademy.domesticappliencesforum.exceptions.AuthorizationException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityDuplicateException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.exceptions.UnauthorizedOperationException;
import com.telerikacademy.domesticappliencesforum.mappers.CommentMapper;
import com.telerikacademy.domesticappliencesforum.mappers.PostMapper;
import com.telerikacademy.domesticappliencesforum.models.*;
import com.telerikacademy.domesticappliencesforum.models.dtos.PostDto;
import com.telerikacademy.domesticappliencesforum.models.dtos.PostFilterDto;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.PostFilterOptions;
import com.telerikacademy.domesticappliencesforum.repositories.PostRepositoryImpl;
import com.telerikacademy.domesticappliencesforum.repositories.VoteRepositoryImpl;
import com.telerikacademy.domesticappliencesforum.services.interfaces.CommentService;
import com.telerikacademy.domesticappliencesforum.services.interfaces.PostService;
import com.telerikacademy.domesticappliencesforum.services.interfaces.TagTypesService;
import com.telerikacademy.domesticappliencesforum.services.interfaces.VoteService;
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
    private final TagTypesService tagTypesService;
    private final CommentService commentService;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final PostRepositoryImpl postRepository;
    private final VoteRepositoryImpl voteRepository;
    private final AuthenticationHelper authenticationHelper;
    private final VoteService voteService;

    @Autowired
    public PostMvcController(PostService postService,
                             TagTypesService tagTypesService,
                             CommentService commentService, PostMapper postMapper, CommentMapper commentMapper, PostRepositoryImpl postRepository,
                             VoteRepositoryImpl voteRepository, AuthenticationHelper authenticationHelper, VoteService voteService) {
        this.postService = postService;
        this.tagTypesService = tagTypesService;
        this.commentService = commentService;
        this.postMapper = postMapper;
        this.commentMapper = commentMapper;
        this.postRepository = postRepository;
        this.voteRepository = voteRepository;
        this.authenticationHelper = authenticationHelper;
        this.voteService = voteService;
    }

    @ModelAttribute("tags")
    public List<TagTypes> populateStyles() {
        return tagTypesService.get();
    }


    @GetMapping
    public String showAllPosts(@ModelAttribute("filter") PostFilterDto filter, Model model) {
        PostFilterOptions filterOptions = new PostFilterOptions(
                filter.getTitle(),
                filter.getUsername(),
                filter.getLocalDate(),
                filter.getLastTen(),
                filter.getTagId(),
                filter.getMostRecently(),
                filter.getMostComments(),
                filter.getMostLiked(),
                filter.getSortBy(),
                filter.getSortOrder()
        );

        List<Post> posts = postService.getAllPosts(filterOptions);
        model.addAttribute("posts", posts);
        model.addAttribute("filter", filter);
        return "posts";
    }

    @GetMapping("/{id}")
    public String showSinglePost(@PathVariable int id, Model model) {
        try {
            Post post = postService.getById(id);
            model.addAttribute("post", post);

            int likes = postRepository.getPostLikes(id);
            if (likes > 0) {
                model.addAttribute("likes", likes);
            }

            int disLikes = postRepository.getPostDisLikes(id);
            if (disLikes > 0) {
                model.addAttribute("disLikes", disLikes);
            }

            return "post";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }
    }

    @GetMapping("/new")
    public String showNewPostPage(Model model, HttpSession session) {
        PostDto post = (PostDto) session.getAttribute("currentPost");
        try {
            authenticationHelper.tryGetCurrentUser(session);
            if (post == null) {
                post = new PostDto();
            } else {
                session.removeAttribute("currentPost");
            }

        } catch (AuthorizationException e) {
            return "redirect:auth/login";
        }
        model.addAttribute("post", post);
        return "post-new";
    }

    @PostMapping("/new")
    public String createPost(@Valid @ModelAttribute("post") PostDto post, BindingResult errors,
                             Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(session);
        } catch (AuthorizationException e) {
            return "redirect:auth/login";
        }
        if (errors.hasErrors()) {
            return "post-new";
        } else if (post.getTagTypeID() == 0) {
            session.setAttribute("currentPost", post);
            return "redirect:/tags/new";
        }
        try {
            Post newPost = postMapper.fromPostDto(post);
            postService.create(newPost, user);
            return "redirect:/posts";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (EntityDuplicateException e) {
            errors.rejectValue("name", "duplicate_post", e.getMessage());
            return "post-new";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }



    @GetMapping("/{id}/update")
    public String showEditPostPage(@PathVariable int id, Model model, HttpSession session) {
        try {
            authenticationHelper.tryGetCurrentUser(session);
        } catch (AuthorizationException e) {
            return "redirect:auth/login";
        }
        try {
            Post post = postService.getById(id);
            PostDto postDto = postMapper.toDto(post);
            model.addAttribute("postId", id);
            model.addAttribute("post", postDto);
            return "post-update";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }
    }

    @PostMapping("/{id}/update")
    public String updatePost(@PathVariable int id, @Valid @ModelAttribute("post") PostDto post, BindingResult errors,
                             Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(session);
        } catch (AuthorizationException e) {
            return "redirect:auth/login";
        }
        if (errors.hasErrors()) {
            return "post-update";
        }
        try {
            Post newPost = postMapper.fromDto(id, post);
            postService.modify(newPost, user);
            return "redirect:/posts";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (EntityDuplicateException e) {
            errors.rejectValue("name", "duplicate_post", e.getMessage());
            return "post-update";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }

    }

    @GetMapping("/{id}/delete")
    public String deletePost(@PathVariable int id, Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(session);
        } catch (AuthorizationException e) {
            return "redirect:auth/login";
        }
        try {
            postService.delete(id, user);
            return "redirect:/posts";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    @GetMapping("/{id}/seeVotes")
    public String seePostVotes(@PathVariable int id, Model model) {
        try {
            List<Vote> votes = voteRepository.getVotesByPostId(id);
            Post post = postService.getById(id);

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
    @PostMapping("{postId}/like")
    public String likePost(@PathVariable("postId") int postId, Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(session);
        } catch (AuthorizationException e) {
            return "redirect:auth/login";
        }
        try {
            Post post = postService.getById(postId);

            VoteTypes voteType=new VoteTypes();
            voteType.setVoteTypeID(1);

            Vote vote = new Vote();
            vote.setPost(post);
            vote.setType(voteType);
            vote.setCreatedBy(user);
            voteService.votePost(vote, user);
        } catch (  EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/posts/" + postId;
        }

        return "redirect:/posts/" + postId;
    }



    @PostMapping("{postId}/dislike")
    public String dislikePost(@PathVariable("postId") int postId, Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(session);
        } catch (AuthorizationException e) {
            return "redirect:auth/login";
        }
        try {
            Post post = postService.getById(postId);

            VoteTypes voteType=new VoteTypes();
            voteType.setVoteTypeID(2);

            Vote vote = new Vote();
            vote.setPost(post);
            vote.setType(voteType);
            vote.setCreatedBy(user);
            voteService.votePost(vote, user);
        } catch (
                EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }

        return "redirect:/posts/" + postId;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }


}
