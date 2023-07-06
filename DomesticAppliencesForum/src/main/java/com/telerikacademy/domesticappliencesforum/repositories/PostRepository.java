package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository {

    List<Post> getAllPosts(String authorId, String localDate, Integer lastTen);
    void create(Post post);
    void modify(Post post);
    void delete(int id);
    Post getPostById(int id);
}
