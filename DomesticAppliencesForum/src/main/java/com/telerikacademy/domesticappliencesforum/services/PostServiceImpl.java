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
    public List<Post> getAllPosts(String title, String authorId, String localDate, Integer lastTen) {
        return postRepository.getAllPosts(title, authorId, localDate, lastTen);
    }

    @Override
    public Post browse(int id) {
        return postRepository.getPostById(id);
    }

    @Override
    public void create(Post post, User user) {
        //TODO Да направим верификация за потребител
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
        if (user.isAdmin()==false || !post.getCreatedBy().getUsername().equals(user.getUsername())) {
            throw new UnauthorizedOperationException("Only admins can delete");
        }
        //TODO Admina ни не работи както трябва!!!
        postRepository.delete(id);
    }
}
