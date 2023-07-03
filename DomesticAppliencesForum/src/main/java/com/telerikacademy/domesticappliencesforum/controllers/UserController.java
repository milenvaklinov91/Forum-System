package com.telerikacademy.domesticappliencesforum.controllers;

import com.telerikacademy.domesticappliencesforum.exceptions.DuplicatePasswordException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityDuplicateException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.mappers.UserMapper;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.dtos.UserDto;
import com.telerikacademy.domesticappliencesforum.services.UserService;
import com.telerikacademy.domesticappliencesforum.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl service;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.service = (UserServiceImpl) userService;
        this.userMapper = userMapper;
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
