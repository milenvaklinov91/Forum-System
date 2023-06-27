package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.models.User;

import java.util.List;

public interface UserRepository {

    List<User> getAll();
    User getUserById(int id);
    User getByUsername(String username);
    User getByPassword(int id, String password);
    User getByEmail(String email);
    void create(User user);
    void update(User user);
    void delete(int id);

}
