package com.telerikacademy.domesticappliencesforum.controllers.mvc;

import com.telerikacademy.domesticappliencesforum.controllers.AuthenticationHelper;
import com.telerikacademy.domesticappliencesforum.exceptions.AuthorizationException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.exceptions.UnauthorizedOperationException;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserMvcController {
    private final UserServiceImpl service;
    private final AuthenticationHelper authenticationHelper;


    @Autowired
    public UserMvcController(UserServiceImpl service, AuthenticationHelper authenticationHelper) {

        this.service = service;

        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public String showAllUsers(Model model) {
        List<User> users = service.getAll();
        model.addAttribute("users", users);
        return "allUsers";
    }

    @GetMapping("/{id}")
    public String getSingleUser(@PathVariable int id, Model model) {
        try {
            User user = service.getById(id);
            model.addAttribute("user", user);
            return "singleUser";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        }
    }

    @GetMapping("/{id}/block")
    public String blockUser(@PathVariable int id, Model model, HttpSession session) {
        try {
            User admin = authenticationHelper.tryGetCurrentUser(session);
            if (!admin.isAdmin()) {
                throw new AuthorizationException("You are not authorized!");
            }
            service.blockUser(id, admin);
            return "redirect:/users";
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    @GetMapping("/{id}/unblock")
    public String unblockUser(@PathVariable int id, Model model, HttpSession session) {
        try {
            User admin = authenticationHelper.tryGetCurrentUser(session);
            if (!admin.isAdmin()) {
                throw new AuthorizationException("You are not authorized!");
            }

            service.unBlockUser(id, admin);
            return "redirect:/users";
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    @GetMapping("/{id}/make-admin")
    public String makeAdmin(@PathVariable int id, Model model, HttpSession session) {
        try {
            User admin = authenticationHelper.tryGetCurrentUser(session);
            if (!admin.isAdmin()) {
                throw new AuthorizationException("You are not authorized!");
            }
            service.makeAdmin(id, admin);
            return "redirect:/users";
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    @GetMapping("/{id}/demote-admin")
    public String demoteAdmin(@PathVariable int id, Model model, HttpSession session) {
        try {
            User admin = authenticationHelper.tryGetCurrentUser(session);
            if (!admin.isAdmin()) {
                throw new AuthorizationException("You are not authorized!");
            }
            service.demoteAdmin(id, admin);
            return "redirect:/users";
        } catch (AuthorizationException e) {
            return "redirect:/auth/login";
        } catch (EntityNotFoundException e) {
            model.addAttribute("error", e.getMessage());
            return "not-found";
        } catch (UnauthorizedOperationException e) {
            model.addAttribute("error", e.getMessage());
            return "AccessDeniedView";
        }
    }

    /*@GetMapping("/new")
    public String showNewUserPage(Model model) {
        model.addAttribute("user", new UserDto());
        return "userRegisterView";
    }*/

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

}
