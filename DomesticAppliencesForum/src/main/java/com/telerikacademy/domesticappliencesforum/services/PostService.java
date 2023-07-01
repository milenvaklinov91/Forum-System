package com.telerikacademy.domesticappliencesforum.services;


import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;

import java.time.LocalDate;
import java.util.List;

public interface PostService {

    List<Post> getAllPosts(String title, int authorId, LocalDate localDate);
    Post browse(int id);
    void create(Post post);
    void modify(Post post);
    void delete(int id);
}
