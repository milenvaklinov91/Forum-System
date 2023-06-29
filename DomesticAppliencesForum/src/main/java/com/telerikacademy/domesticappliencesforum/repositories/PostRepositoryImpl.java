package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.Post;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final List<Post> posts;
    private int id;

    public PostRepositoryImpl() {
        this.posts = new ArrayList<>();

        Post post1 = new Post("title","content" ,1, LocalDate.now());
        post1.setPostId(++id);
        posts.add(post1);
        Post post2 = new Post("title","content" ,2, LocalDate.now());
        post2.setPostId(++id);
        posts.add(post2);

    }

    @Override
    public List<Post> getAllPosts() {
        return new ArrayList<>(posts);
    }

    @Override
    public Post getPostById(int id) {
        return posts.stream()
                .filter(post -> post.getPostId() == id)
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
        post.setPostId(++id);
        posts.add(post);
    }

    @Override
    public void modify(Post post) {
        Post postToUpdate = getPostById(post.getPostId());
        postToUpdate.setTitle(post.getTitle());
        postToUpdate.setContent(post.getContent());
    }

    @Override
    public void delete(int id) {
        Post postToDelete = getPostById(id);
        posts.remove(postToDelete);
    }

}
