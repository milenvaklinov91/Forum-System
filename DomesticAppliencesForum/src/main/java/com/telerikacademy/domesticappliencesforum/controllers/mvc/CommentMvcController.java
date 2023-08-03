package com.telerikacademy.domesticappliencesforum.controllers.mvc;

import com.telerikacademy.domesticappliencesforum.controllers.AuthenticationHelper;
import com.telerikacademy.domesticappliencesforum.exceptions.AuthorizationException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityDuplicateException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.exceptions.UnauthorizedOperationException;
import com.telerikacademy.domesticappliencesforum.mappers.CommentMapper;
import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.dtos.CommentDto;
import com.telerikacademy.domesticappliencesforum.models.dtos.PostDto;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.FilterOptionsComment;
import com.telerikacademy.domesticappliencesforum.repositories.CommentRepositoryImpl;
import com.telerikacademy.domesticappliencesforum.services.interfaces.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentMvcController {
    private final CommentService commentService;
    private final CommentRepositoryImpl commentRepository;
    private final AuthenticationHelper authenticationHelper;
    private final CommentMapper modelMapper;

    @Autowired
    public CommentMvcController(CommentService commentService
            , CommentRepositoryImpl commentRepository, AuthenticationHelper authenticationHelper, CommentMapper modelMapper) {
        this.commentService = commentService;
        this.commentRepository = commentRepository;
        this.authenticationHelper = authenticationHelper;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public String showSingleComment(@PathVariable int id, Model model) {
        try {
            Comment comment = commentService.getCommentById(id);
            model.addAttribute("comment", comment);
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
    public String createNewComment(@Valid @ModelAttribute("comment") CommentDto commentDto, BindingResult errors,
                                   Model model, HttpSession session) {
        User user;
        try {
            user = authenticationHelper.tryGetCurrentUser(session);
        } catch (AuthorizationException e) {
            return "redirect:auth/login";
        }
        if (errors.hasErrors()) {
            return "comment-new";
        }

        try {
            Comment newComment = modelMapper.fromCommentDto(commentDto);
            commentService.create(newComment, user);
            return "redirect:/comments";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (EntityDuplicateException e) {
            errors.rejectValue("name", "duplicate_comment", e.getMessage());
            return "comment-new";
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
            CommentDto commentDto = modelMapper.toDto(comment);
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
            Comment newComment = modelMapper.fromCommentDtoWithID(id, commentDto);
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
}

