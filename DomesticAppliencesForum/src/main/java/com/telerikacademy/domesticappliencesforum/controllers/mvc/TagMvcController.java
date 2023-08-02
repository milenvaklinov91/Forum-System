package com.telerikacademy.domesticappliencesforum.controllers.mvc;

import com.telerikacademy.domesticappliencesforum.controllers.AuthenticationHelper;
import com.telerikacademy.domesticappliencesforum.exceptions.AuthorizationException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityDuplicateException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.exceptions.UnauthorizedOperationException;
import com.telerikacademy.domesticappliencesforum.models.TagTypes;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.services.interfaces.TagTypesService;
import com.telerikacademy.domesticappliencesforum.services.interfaces.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/tags")
public class TagMvcController {
    private final TagTypesService tagTypesService;
    private final UserService userService;

    private final AuthenticationHelper authenticationHelper;

    public TagMvcController(TagTypesService tagTypesService, UserService userService, AuthenticationHelper authenticationHelper) {
        this.tagTypesService = tagTypesService;
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public String showAllTags(Model model) {
        List<TagTypes> tags = tagTypesService.get();
        model.addAttribute("tags", tags);
        return "tags";

    }

    @GetMapping("/{id}")
    public String showSingleTag(@PathVariable int id, Model model) {
        try {
            TagTypes tag = tagTypesService.getById(id);
            model.addAttribute("tag", tag);
            return "tag";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }

    }

    @GetMapping("/new")
    public String showNewTagPage(Model model) {
        try {
            model.addAttribute("tag", new TagTypes());
            return "tag-new";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }
    }

    @PostMapping("/new")
    public String createTag(@Valid @ModelAttribute("tag") TagTypes tag, BindingResult errors,
                            Model model, HttpSession session) {
        User user = authenticationHelper.tryGetCurrentUser(session);
        if (user.isBlocked()) {
            throw new AuthorizationException("You are not authorized!");
        }
        if (errors.hasErrors()) {
            return "tag-new";
        }
        try {
            tagTypesService.create(tag);
            return "redirect:/posts/new";
        } catch (EntityDuplicateException e) {
            errors.rejectValue("type", "tag.exist", e.getMessage());
            return "tag-new";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

}
