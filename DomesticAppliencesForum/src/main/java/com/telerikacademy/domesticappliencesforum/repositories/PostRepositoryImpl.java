package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final List<Post> posts;
    private int id;

    @Autowired
    public PostRepositoryImpl() {
        this.posts = new ArrayList<>();

        Post post1 = new Post("title", "content", 1);
        post1.setPostId(++id);
        posts.add(post1);
        Post post2 = new Post("title", "content", 2);
        post2.setPostId(++id);
        posts.add(post2);

    }

    @Override
    public List<Post> getAllPosts(String title, Integer authorId, String localDate) {
        List<Post> result = new ArrayList<>(posts);
        result = filterByAuthor(result, authorId);
        result = filterByDate(result, localDate);
        result = filterLastTenCreatedPosts(result);
        return result;
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

    private List<Post> filterByAuthor(List<Post> posts, Integer authorId) {
        if (posts != null && authorId != null) {
            posts = posts.stream()
                    .filter(post -> post.getAuthorId() == authorId)
                    .collect(Collectors.toList());
        }
        return posts;
    }


    private List<Post> filterByDate(List<Post> posts, String localDate) {
        if (posts != null && localDate != null) {
            posts = posts.stream()
                    .filter(post -> post.getCreateDate().equals(localDate))
                    .collect(Collectors.toList());
        }
        return posts;
    }

    private List<Post> filterLastTenCreatedPosts(List<Post> posts) {
        if (posts != null) {
            posts = posts.stream().sorted(Comparator
                    .comparing(Post::getPostId)
                    .reversed()).limit(10).collect(Collectors.toList());
        }
        return posts;
    }

}
