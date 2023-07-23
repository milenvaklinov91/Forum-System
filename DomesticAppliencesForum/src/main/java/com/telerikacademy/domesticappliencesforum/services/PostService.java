package com.telerikacademy.domesticappliencesforum.services;


import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.PostFilterOptions;

import java.util.List;

public interface PostService {

    List<Post> getAllPosts(PostFilterOptions filterOptions);
    Long countAllPosts();
    Post getById(int id);

    void create(Post post, User user);

    void modify(Post post, User user);

    void delete(int id, User user);

    List<Comment> getAllComments(int id);
    int getPostLikes(int postId);
    int getPostDisLikes(int postId);
}
