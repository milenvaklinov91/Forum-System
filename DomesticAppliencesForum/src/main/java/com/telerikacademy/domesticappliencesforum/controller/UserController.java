package com.telerikacademy.domesticappliencesforum.controller;

import com.telerikacademy.domesticappliencesforum.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final List<User> users;

    public UserController() {
        users = new ArrayList<>();

        users.add(new User(1, "milenvaklinov", "milen",
                "vaklinov", "milen91@abv.bg", "milen91"));
        users.add(new User(2, "ledayovkova", "leda",
                "yovkova", "leda@abv.bg", "leda123"));
    }

    @GetMapping
    public List<User> getAll(){
        return users;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id){
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                String.format("User with id %d not found.", id)
        ));
    }
    @PostMapping
    public User create(@Valid @RequestBody User user){
        users.add(user);
        return user;
    }
}
