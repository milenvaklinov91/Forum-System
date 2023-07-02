package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.exceptions.UnauthorizedOperationException;
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
    private UserRepository userRepository;

    @Autowired
    public PostServiceImpl(UserRepository userRepository) {
        this.postRepository = new PostRepositoryImpl(userRepository);

    }

    @Override
    public List<Post> getAllPosts(String title, User authorId, String localDate) {
        return postRepository.getAllPosts(title, authorId, localDate);
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
    public void modify(Post post, User user) {
        if (post.getAuthorId().equals(user.getId())){
            throw new UnauthorizedOperationException("Admins cannot modify posts");
        }
        //TODO Да направим верификация за потребител да може да променя само неговите си постове
        postRepository.modify(post);
    }

    @Override
    public void delete(int id, User user) {
        Post post = postRepository.getPostById(id);
        if(!user.isAdmin() || !post.getAuthorId().equals(user.getId())){
            throw new UnauthorizedOperationException("Only admins can delete");
        }
        //TODO Да направим верификация за потребител да може да променя само
        // неговите си постове и админа - всички
        postRepository.delete(id);
    }
}
