package com.telerikacademy.domesticappliencesforum.controllers.mvc;

import com.telerikacademy.domesticappliencesforum.controllers.AuthenticationHelper;
import com.telerikacademy.domesticappliencesforum.exceptions.AuthorizationException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityDuplicateException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.exceptions.UnauthorizedOperationException;
import com.telerikacademy.domesticappliencesforum.mappers.CommentMapper;
import com.telerikacademy.domesticappliencesforum.mappers.PostMapper;
import com.telerikacademy.domesticappliencesforum.models.*;
import com.telerikacademy.domesticappliencesforum.models.dtos.CommentDto;
import com.telerikacademy.domesticappliencesforum.models.dtos.PostDto;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.FilterOptionsComment;
import com.telerikacademy.domesticappliencesforum.repositories.CommentRepositoryImpl;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.VoteCommentRepository;
import com.telerikacademy.domesticappliencesforum.services.interfaces.CommentService;
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
@RequestMapping("/comments")
public class CommentMvcController {
    private final CommentService commentService;

    private final VoteCommentRepository voteCommentRepository;
    private final CommentRepositoryImpl commentRepository;
    private final AuthenticationHelper authenticationHelper;
    private final CommentMapper commentMapper;
    private final PostMapper postMapper;

    @Autowired
    public CommentMvcController(CommentService commentService
            , VoteCommentRepository voteCommentRepository, CommentRepositoryImpl commentRepository, AuthenticationHelper authenticationHelper, CommentMapper commentMapper, PostMapper postMapper) {
        this.commentService = commentService;
        this.voteCommentRepository = voteCommentRepository;
        this.commentRepository = commentRepository;
        this.authenticationHelper = authenticationHelper;
        this.commentMapper = commentMapper;
        this.postMapper = postMapper;
    }

    @GetMapping("/{id}")
    public String showSingleComment(@PathVariable int id, Model model) {
        try {
            Comment comment = commentService.getCommentById(id);
            model.addAttribute("comment", comment);

            int commentLikes = commentRepository.getCommentLikes(id);
            if (commentLikes > 0) {
                model.addAttribute("commentLikes", commentLikes);
            }

            int commentDisLikes = commentRepository.getCommentDisLikes(id);
            if (commentDisLikes > 0) {
                model.addAttribute("commentDisLikes", commentDisLikes);
            }
            return "comment";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }
    }

    @GetMapping
    public String showAllComments(Model model) {
        List<Comment> comments = commentService.getAllComments(new FilterOptionsComment());
        model.addAttribute("comments", comments);
        return "comments";
    }

    @GetMapping("/new")
    public String showNewCommentPage(Model model, HttpSession session) {
        CommentDto comment = (CommentDto) session.getAttribute("currentComment");
        try {
            authenticationHelper.tryGetCurrentUser(session);
            if (comment == null) {
                comment = new CommentDto();
            } else {
                session.removeAttribute("currentComment");
            }

        } catch (AuthorizationException e) {
            return "redirect:auth/login";
        }
        model.addAttribute("comment", comment);
        return "comment-new";
    }

    @PostMapping("/new")
    public String createNewComment( @Valid @ModelAttribute("comment") CommentDto commentDto, PostDto postDto,
                                    BindingResult errors, Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(session);
        } catch (AuthorizationException e) {
            return "redirect:auth/login";
        }
        if (errors.hasErrors()) {
            return "redirect:/posts/";
        }
        try {
            Comment newComment = commentMapper.fromCommentDto(commentDto);
            commentService.create(newComment,user);
            int postId=newComment.getPostId().getPostId();
            return "redirect:/posts/" + postId;
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    @GetMapping("/{id}/update")
    public String showEditCommentPage(@PathVariable int id, Model model, HttpSession session) {
        try {
            authenticationHelper.tryGetCurrentUser(session);
        } catch (AuthorizationException e) {
            return "redirect:auth/login";
        }
        try {
            Comment comment = commentService.getCommentById(id);
            CommentDto commentDto = commentMapper.toDto(comment);
            model.addAttribute("commentId", id);
            model.addAttribute("comment", commentDto);
            return "comment-update";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }
    }

    @PostMapping("/{id}/update")
    public String updateComment(@PathVariable int id, @Valid @ModelAttribute("comment") CommentDto commentDto, BindingResult errors,
                                Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(session);
        } catch (AuthorizationException e) {
            return "redirect:auth/login";
        }
        if (errors.hasErrors()) {
            return "comment-update";
        }
        try {
            Comment newComment = commentMapper.fromCommentDtoWithID(id, commentDto);
            commentService.modify(newComment, user);
            return "redirect:/comments";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (EntityDuplicateException e) {
            errors.rejectValue("name", "duplicate_comment", e.getMessage());
            return "comment-update";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteComment(@PathVariable int id, Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(session);
        } catch (AuthorizationException e) {
            return "redirect:auth/login";
        }
        try {
            commentService.delete(id, user);
            return "redirect:/comments";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    @GetMapping("/{id}/seeVotes")
    public String seeCommentVotes(@PathVariable int id, Model model) {
        try {
            List<VoteComment> votes = voteCommentRepository.getVoteCommentByCommentId(id);
            Comment comment = commentService.getCommentById(id);

            int likes = 0;
            int dislikes = 0;
            List<String> usersWhoLiked = new ArrayList<>();
            List<String> usersWhoDisliked = new ArrayList<>();

            for (VoteComment vote : votes) {
                if (vote.getTypeId().getVoteTypeID() == 1) {
                    likes++;
                    usersWhoLiked.add(vote.getCreatedBy().getUsername());
                } else if (vote.getTypeId().getVoteTypeID() == 2) {
                    dislikes++;
                    usersWhoDisliked.add(vote.getCreatedBy().getUsername());
                }
            }

            model.addAttribute("commentId", id);
            model.addAttribute("comment", comment);
            model.addAttribute("likes", likes);
            model.addAttribute("dislikes", dislikes);
            model.addAttribute("usersWhoLiked", usersWhoLiked);
            model.addAttribute("usersWhoDisliked", usersWhoDisliked);
        } catch (EntityNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
        }
        return "voteForComments";
    }

    //todo

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

}

