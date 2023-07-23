package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.PostFilterOptions;

import java.util.List;

public interface PostRepository {

    List<Post> getAllPosts(PostFilterOptions filterOptions);
    Long countAllPosts();
    void create(Post post);

    void modify(Post post);

    void delete(int id);

    Post getPostById(int id);
    int getPostLikes(int postId);
    int getPostDisLikes(int postId);
}
