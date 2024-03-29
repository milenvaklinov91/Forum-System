package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.exceptions.UnauthorizedOperationException;
import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.PostFilterOptions;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.PostRepository;
import com.telerikacademy.domesticappliencesforum.services.interfaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAllPosts(PostFilterOptions filterOptions) {
        return postRepository.getAllPosts(filterOptions);
    }

    public Long countAllPosts() {
        return postRepository.countAllPosts();
    }

    @Override
    public Post getById(int id) {
        return postRepository.getPostById(id);
    }

    @Override
    public void create(Post post, User user) {
        post.setCreatedBy(user);
        if (post.getCreatedBy().isBlocked()) {
            throw new UnauthorizedOperationException("You`re blocked!!!");
        }
        postRepository.create(post);
    }


    @Override
    public void modify(Post post, User user) {
        if (user.isBlocked()) {
            throw new UnauthorizedOperationException("You`re blocked!!!");
        } else if (!(post.getCreatedBy().getUsername().equals(user.getUsername()))) {
            throw new UnauthorizedOperationException("You're not authorized for this operation");
        }
        postRepository.modify(post);
    }

    @Override
    public void delete(int id, User user) {
        Post post = postRepository.getPostById(id);
        if (user.isBlocked()) {
            throw new UnauthorizedOperationException("You`re blocked!!!");
        } else if (!(user.isAdmin() || post.getCreatedBy().getUsername().equals(user.getUsername()))) {
            throw new UnauthorizedOperationException("You're not authorized for this operation");
        }
        postRepository.delete(id);
    }

    public List<Comment> getAllComments(int id) {
        List<Comment> allComments = postRepository.getPostById(id).getComments();
        if (allComments.isEmpty()) {
            throw new EntityNotFoundException("This post dont have comments");
        }
        return new ArrayList<>(allComments);
    }

    public int getPostLikes(int postId) {

        return postRepository.getPostLikes(postId);
    }

    public int getPostDisLikes(int postId) {
        return postRepository.getPostDisLikes(postId);
    }
}