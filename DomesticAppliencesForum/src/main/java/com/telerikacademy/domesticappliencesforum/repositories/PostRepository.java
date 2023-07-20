package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.models.Post;

import java.util.List;

public interface PostRepository {

    List<Post> getAllPosts(String userName, String localDate, Integer lastTen, Integer tagId, String mostComments);
    Long countAllPosts();
    void create(Post post);

    void modify(Post post);

    void delete(int id);

    Post getPostById(int id);
    int getPostLikes(int postId);
    int getPostDisLikes(int postId);
}
