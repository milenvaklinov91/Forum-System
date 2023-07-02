package com.telerikacademy.domesticappliencesforum.services;


import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts(String title, String authorId, String localDate,Integer lastTen);
    Post browse(int id);

    void create(Post post, User user);

    void modify(Post post, User user);
    void delete(int id, User user);
}
