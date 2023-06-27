package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.exceptions.DuplicatePasswordException;
import com.telerikacademy.domesticappliencesforum.exceptions.EmailExitsException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.enums.GenderTypes;

import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl {
    private final List<User> users;

    public UserRepositoryImpl() {
        users = new ArrayList<>();

        users.add(new User(1, "milenvaklinov", "milen",
                "vaklinov", "milen91@abv.bg", "milen91", GenderTypes.Male));
        users.add(new User(2, "ledayovkova", "leda",
                "yovkova", "leda@abv.bg", "leda123", GenderTypes.Female));
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
                .orElseThrow(() -> new EntityNotFoundException("User", username));

    }

    public User getByPassword(int id, String password) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .filter(user -> user.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new DuplicatePasswordException(password));
    }

    public User getByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new EmailExitsException(email));
    }

    public void create(User user) {
        users.add(user);
    }

    public void update(User user) {
        User userToUpdate = getUserById(user.getId());
        userToUpdate.setPassword(user.getPassword());
    }

    public void delete(int id) {
        User userToDelete = getUserById(id);
        users.remove(userToDelete);
    }
}