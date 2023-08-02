package com.telerikacademy.domesticappliencesforum.controllers.mvc;

import com.telerikacademy.domesticappliencesforum.controllers.AuthenticationHelper;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.mappers.CommentMapper;
import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.FilterOptionsComment;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.PostFilterOptions;
import com.telerikacademy.domesticappliencesforum.repositories.CommentRepositoryImpl;
import com.telerikacademy.domesticappliencesforum.repositories.VoteCommentRepositoryImpl;
import com.telerikacademy.domesticappliencesforum.services.interfaces.CommentService;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentMvcController {
    private final CommentService commentService;
    private final CommentRepositoryImpl commentRepository;
    @Autowired
    public CommentMvcController(CommentService commentService
            ,CommentRepositoryImpl commentRepository) {
        this.commentService = commentService;
        this.commentRepository = commentRepository;

    }

    @GetMapping("/{id}")
    public String showSingleComment(@PathVariable int id, Model model) {
//        try {
            Comment comment = commentService.getCommentById(id);
             model.addAttribute("comment", comment);
            return "comment";
//        } catch (EntityNotFoundException e) {
//            model.addAttribute("error", e.getMessage());
//            return "not-found";
//        }
    }
//    @GetMapping
//    public String showAllComments(Model model) {
//        List<Comment> comments = commentService.getAllComments(new FilterOptionsComment());
//        model.addAttribute("comments", comments);
//        return "comments";
//    }
}
