package com.telerikacademy.domesticappliencesforum.controllers.mvc;

import com.telerikacademy.domesticappliencesforum.controllers.AuthenticationHelper;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.mappers.UserMapper;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.dtos.user.UserDto;
import com.telerikacademy.domesticappliencesforum.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserMvcController {
    private final UserServiceImpl service;
    private final UserMapper userMapper;
    private final AuthenticationHelper authenticationHelper;

    @Autowired
    public UserMvcController(UserServiceImpl service, UserMapper userMapper,
                             AuthenticationHelper authenticationHelper) {

        this.service = service;
        this.userMapper = userMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public String showAllUsers(Model model) {
        List<User> users = service.getAll();
        model.addAttribute("users", users);
        return "allUsers";
    }

    /*@GetMapping("/count")
    public String countAllUsers() {
        List<User> users = service.countAllUsers();
        return null;
    }*/

    @GetMapping("/{id}")
    public String  getSingleUser(@PathVariable int id,Model model) {
        try {
            User user = service.getById(id);
            model.addAttribute("user", user);
            return "user";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }
    }

    @GetMapping("/new")
    public String showNewUserPage(Model model) {
        model.addAttribute("user", new UserDto());
        return "user-new";
    }

}
