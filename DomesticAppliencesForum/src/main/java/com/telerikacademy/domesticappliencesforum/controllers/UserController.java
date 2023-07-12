package com.telerikacademy.domesticappliencesforum.controllers;

import com.telerikacademy.domesticappliencesforum.exceptions.*;
import com.telerikacademy.domesticappliencesforum.mappers.UserMapper;
import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.dtos.UserDto;
import com.telerikacademy.domesticappliencesforum.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl service;
    private final UserMapper userMapper;
    private final AuthenticationHelper authenticationHelper;


    @Autowired
    public UserController(UserServiceImpl service, UserMapper userMapper,
                          AuthenticationHelper authenticationHelper) {

        this.service = service;
        this.userMapper = userMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<User> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        try {
            return service.getById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage());
        }
    }

    @GetMapping("/username")
    public User getUserByUsername(@RequestHeader HttpHeaders headers, String username) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            service.getUserDetails(user.getId(), user);
            return service.getByUsername(username);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage());
        }
    }

    @GetMapping("/firstname")
    public User getUserByFirstName(@RequestHeader HttpHeaders headers, String firstName) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            service.getUserDetails(user.getId(), user);
            return service.getByFirstName(firstName);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage());
        }
    }

    @GetMapping("/email")
    public User getUserByEmail(@RequestHeader HttpHeaders headers, String email) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            service.getUserDetails(user.getId(), user);
            return service.getByEmail(email);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    e.getMessage());
        }
    }

    @GetMapping("/{id}/allposts")
    public List<Post> getAllPost(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        service.getUserDetails(user.getId(), user);
        Set<Post> allPost = (getUserById(id).getPost());
        return new ArrayList<>(allPost);
    }

    @GetMapping("/{id}/allComments")
    public List<Comment> getAllComments(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        User user = authenticationHelper.tryGetUser(headers);
        service.getUserDetails(user.getId(), user);
        Set<Comment> allComment = (getUserById(id).getComments());
        return new ArrayList<>(allComment);
    }

    /*//todo
    @GetMapping("/{id}/details")
    public UserLoginDetails getDetail(@RequestHeader HttpHeaders headers,@PathVariable int id ) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            return service.getUserDetails(id,user);
        } catch (AuthorizationException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }*/

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
    public User update(@Valid @RequestBody User user) {
        try {
            service.update(user);
            return user;
        } catch (DuplicatePasswordException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        try {
            service.delete(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
