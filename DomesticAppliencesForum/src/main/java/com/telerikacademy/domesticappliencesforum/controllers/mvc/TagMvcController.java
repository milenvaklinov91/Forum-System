package com.telerikacademy.domesticappliencesforum.controllers.mvc;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.TagTypes;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.dtos.PostDto;
import com.telerikacademy.domesticappliencesforum.services.interfaces.TagTypesService;
import com.telerikacademy.domesticappliencesforum.services.interfaces.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/tags")
public class TagMvcController {
    private final TagTypesService tagTypesService;
    private final UserService userService;

    public TagMvcController(TagTypesService tagTypesService, UserService userService) {
        this.tagTypesService = tagTypesService;
        this.userService = userService;
    }

    @GetMapping
    public String showAllTags(Model model){
        List<TagTypes> tags=tagTypesService.get();
        model.addAttribute("tags",tags);
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
        model.addAttribute("tag", new TagTypes());
        return "tag-new";
    }

    @PostMapping("/new")
    public String createTag(@ModelAttribute TagTypes tag) {
        tagTypesService.create(tag);
        return "redirect:/tags";
    }
}
