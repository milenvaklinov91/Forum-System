package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.exceptions.UnauthorizedOperationException;
import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Post> getAllPosts(String userName, String localDate, Integer lastTen, Integer tagId, String mostComment) {
        return postRepository.getAllPosts(userName, localDate, lastTen, tagId, mostComment);
    }

    @Override
    public Post getById(int id) {
        return postRepository.getPostById(id);
    }

    @Override
    public void create(Post post, User user) {
        post.setCreatedBy(user);
        postRepository.create(post);
    }

    @Override
    public void modify(Post post, User user) {
        if (!(post.getCreatedBy().getUsername().equals(user.getUsername()))) {
            throw new UnauthorizedOperationException("You're not authorized for this operation");
        }
        postRepository.modify(post);
    }

    @Override
    public void delete(int id, User user) {
        Post post = postRepository.getPostById(id);
        if (!(user.isAdmin() || post.getCreatedBy().getUsername().equals(user.getUsername()))) {
            throw new UnauthorizedOperationException("You're not authorized for this operation");
        }
        postRepository.delete(id);
    }

    public List<Comment> getAllComments(int id) {
        Set<Comment> allComments = postRepository.getPostById(id).getComments();
        return new ArrayList<>(allComments);
    }
}