package com.telerikacademy.domesticappliencesforum.services;


import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts(String title, User authorId, String localDate);
    Post browse(int id);
    void create(Post post);
    void modify(Post post, User user);
    void delete(int id, User user);
}
