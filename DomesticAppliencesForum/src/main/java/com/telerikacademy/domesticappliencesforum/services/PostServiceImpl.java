package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.exceptions.UnauthorizedOperationException;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;

    }

    @Override
    public List<Post> getAllPosts(String userName, String localDate, Integer lastTen,Integer tagId) {
        return postRepository.getAllPosts( userName, localDate, lastTen,tagId);
    }

    @Override
    public Post browse(int id) {
        return postRepository.getPostById(id);
    }

    @Override
    public void create(Post post, User user) {
        post.setCreatedBy(user);
        postRepository.create(post);
    }

    @Override
    public void modify(Post post, User user) {
        if (!(post.getCreatedBy().getLoginDetails().getUsername().equals(user.getLoginDetails().getUsername()))) {
            throw new UnauthorizedOperationException("You're not authorized for this operation");
        }
        postRepository.modify(post);
    }

    @Override
    public void delete(int id, User user) {
        Post post = postRepository.getPostById(id);
        if (!(user.isAdmin() || post.getCreatedBy().getLoginDetails().getUsername().equals(user.getLoginDetails().getUsername()))) {
            throw new UnauthorizedOperationException("Only admins can delete");
        }
        postRepository.delete(id);
    }
}
