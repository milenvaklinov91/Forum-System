package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;

import java.util.List;

public interface UserRepository {

    List<User> getAll();

    Long countAllUsers();

    User getUserById(int id);

    User getByUsername(String username);

    User getByFirstName(String firstName);

    User getByEmail(String email);


    List<Post> getLikedPostsByUser(int userId);

    List<Post> getDisLikedPostsByUser(int userId);

    void create(User user);

    void update(User user);

    /*void delete(int id);*/


}
