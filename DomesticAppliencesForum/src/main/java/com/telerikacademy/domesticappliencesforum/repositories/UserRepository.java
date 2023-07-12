package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.models.User;

import java.util.List;

public interface UserRepository {

    List<User> getAll();
    User getUserById(int id);
    User getByUsername(String username);
    User getByFirstName (String firstName);
    User getByEmail(String email);
    User getByPassword(int id, String password);
    void create(User user);
    void update(User user);
    void delete(int id);

}
