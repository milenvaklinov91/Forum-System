package com.telerikacademy.domesticappliencesforum.controllers.mvc;

import com.telerikacademy.domesticappliencesforum.controllers.AuthenticationHelper;
import com.telerikacademy.domesticappliencesforum.exceptions.DuplicatePasswordException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityDuplicateException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.mappers.UserMapper;
import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.dtos.UserDto;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.PostFilterOptions;
import com.telerikacademy.domesticappliencesforum.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
        return "users";
    }

    @GetMapping("/count")
    public String countAllPosts() {
        return null;
    }

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

    @GetMapping("/username")
    public User getUserByUsername(@RequestHeader HttpHeaders headers, String username) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            service.getUserDetails(user.getId(), user);
            return service.getByUsername(username);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/firstname")
    public User getUserByFirstName(@RequestHeader HttpHeaders headers, String firstName) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            service.getUserDetails(user.getId(), user);
            return service.getByFirstName(firstName);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/email")
    public User getUserByEmail(@RequestHeader HttpHeaders headers, String email) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            service.getUserDetails(user.getId(), user);
            return service.getByEmail(email);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /*@GetMapping("/{id}/all-posts")
    public List<Post> getAllPost(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        service.getUserDetails(user.getId(), user);
        Set<Post> allPost = (getUserById(id).getPost());
        return new ArrayList<>(allPost);
    }

    @GetMapping("/{id}/all-comments")
    public List<Comment> getAllComments(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        service.getUserDetails(user.getId(), user);
        Set<Comment> allComment = (getUserById(id).getComments());
        return new ArrayList<>(allComment);
    }*/
    @GetMapping("/{id}/liked-posts")
    public List<Post> getLikedPostsByUser(@PathVariable int id) {
        return service.getLikedPostsByUser(id);
    }

    @GetMapping("/{id}/disLiked-posts")
    public List<Post> getDisLikedPostsByUser(@PathVariable int id) {
        return service.getDisLikedPostsByUser(id);
    }

    @PostMapping
    public User create(@Valid @RequestBody UserDto userDto) {
        try {
            User user = userMapper.fromUserDto(userDto);
            service.create(user);
            return user;
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public String update(@RequestHeader HttpHeaders headers,@PathVariable int id,
                         @Valid @RequestBody UserDto userDto) {
        try {
            User loggedUser = authenticationHelper.tryGetUser(headers);
            User user = userMapper.fromUserDto(id,userDto);
            service.update(loggedUser,user);
            return "User was successfully updated!";
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/block")
    public User blockUser(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return service.blockUser(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/unblock")
    public User unBlockUser(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return service.unBlockUser(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/make-admin")
    public User makeAdmin(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return service.makeAdmin(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/unmake-admin")
    public User unMakeAdmin(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return service.unMakeAdmin(id, user);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }


}
