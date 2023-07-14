package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.UserLoginDetails;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getById(int id);

    User getByUsername(String username);

    User getByFirstName(String firstName);

    User getByEmail(String email);

    UserLoginDetails getUserDetails(int id, User user);

    void create(User user);

    void update(User user);

    void delete(int id);


}
