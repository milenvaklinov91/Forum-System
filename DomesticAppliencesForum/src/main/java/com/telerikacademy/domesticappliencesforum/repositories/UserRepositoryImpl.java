package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl {
    private final List<User> users;

    public UserRepositoryImpl() {
        users = new ArrayList<>();

        users.add(new User(1, "milenvaklinov", "milen",
                "vaklinov", "milen91@abv.bg", "milen91"));
        users.add(new User(2, "ledayovkova", "leda",
                "yovkova", "leda@abv.bg", "leda123"));
    }

    public List<User> getAll() {
        return users;
    }

    public User getUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("User", id));
    }

    public User getByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("User", "username", username));

    }


}
