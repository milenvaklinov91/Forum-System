package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getById(int id);

    User getByUsername(String username);

    User getByFirstName(String firstName);

    User getByEmail(String email);

    User getUserDetails(int id, User user);
    List<Post> getDisLikedPostsByUser(int userId);

    List<Post> getLikedPostsByUser(int userId);

    void update(User user, User user1);

    User blockUser(int id, User user);

    User unBlockUser(int id, User user);

    void delete(int id);


}
