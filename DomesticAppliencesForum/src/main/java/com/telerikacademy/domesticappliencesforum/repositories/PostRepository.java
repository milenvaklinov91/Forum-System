package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;

import java.util.List;

public interface PostRepository {

    Post browse(int id);
    void create(Post post);
    void modify(Post post);
    void delete(int id);
}
