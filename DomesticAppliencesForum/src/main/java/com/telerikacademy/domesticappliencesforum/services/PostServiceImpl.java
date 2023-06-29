package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.repositories.PostRepository;
import com.telerikacademy.domesticappliencesforum.repositories.PostRepositoryImpl;
import com.telerikacademy.domesticappliencesforum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl() {
        this.postRepository = new PostRepositoryImpl();

    }
    @Override
    public List<Post> getAllPosts() {
        return postRepository.getAllPosts();
    }

    @Override
    public Post browse(int id) {
        return postRepository.getPostById(id);
    }

    @Override
    public void create(Post post) {
        //TODO Да направим верификация за потребител
        postRepository.create(post);
    }

    @Override
    public void modify(Post post) {
        //TODO Да направим верификация за потребител да може да променя само неговите си постове
        postRepository.modify(post);
    }

    @Override
    public void delete(int id) {
        //TODO Да направим верификация за потребител да може да променя само
        // неговите си постове и админа - всички
        postRepository.delete(id);
    }
}
