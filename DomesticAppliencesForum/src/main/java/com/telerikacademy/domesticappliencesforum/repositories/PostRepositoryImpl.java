package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PostRepositoryImpl implements PostRepository {

    List<Post> posts;
    private int id = 1;

    public PostRepositoryImpl(UserRepository userRepository) {
        this.posts = new ArrayList<>();

        Post post1 = new Post("title","content" ,userRepository.getByUsername("milenvaklinov"), LocalDate.now());
        post1.setId(id++);
        posts.add(post1);
        Post post2 = new Post("title","content" ,userRepository.getByUsername("ledayovkova"), LocalDate.now());
        post2.setId(id++);
        posts.add(post2);

    }

    public Post getPostById(int id) {
        return posts.stream()
                .filter(post -> post.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Post", id));
    }

    @Override
    public Post browse(int id) {
        Post postToReturn = getPostById(id);
        return postToReturn;
    }

    @Override
    public void create(Post post) {
        post.setId(id++);
        posts.add(post);
    }

    @Override
    public void modify(Post post) {
        Post postToUpdate = getPostById(post.getId());
        postToUpdate.setTitle(post.getTitle());
        postToUpdate.setContent(post.getContent());
    }

    @Override
    public void delete(int id) {
        Post postToDelete = getPostById(id);
        posts.remove(postToDelete);
    }

    public List<Post> getByUsername(User user) {
        return posts.stream()
                .filter(post -> post.getCreatedBy().equals(user.getUsername()))
                .collect(Collectors.toList());
    }

}
