package com.telerikacademy.domesticappliencesforum.services;


import com.telerikacademy.domesticappliencesforum.models.Post;

public interface PostService {
    Post browse(int id);
    void create(Post post);
    void modify(Post post);
    void delete(int id);
}
