package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.models.User;

import java.util.List;

public interface UserService {

    List<User> getAll();
    User getById(int id);
    void create(User user);
    void update(User user);
    void delete(int id);

}
