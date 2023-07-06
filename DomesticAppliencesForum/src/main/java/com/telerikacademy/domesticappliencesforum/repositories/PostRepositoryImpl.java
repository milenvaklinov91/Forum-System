package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
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
    public PostRepositoryImpl(UserRepository userRepository) {
        /*this.posts = new ArrayList<>();

        Post post1 = new Post("title", "content");
        post1.setCreatedBy(userRepository.getUserById(1));
        post1.setPostId(++id);
        posts.add(post1);
        Post post2 = new Post("title", "content");
        post2.setCreatedBy(userRepository.getUserById(2));
        post2.setPostId(++id);
        posts.add(post2);*/
    }

    @Override
    public List<Post> getAllPosts(String title, String authorId, String localDate, Integer lastTen) {
        List<Post> result = new ArrayList<>(posts);
        result = filterByAuthor(result, authorId);
        result = filterByDate(result, localDate);
        //TODO Дори с request пак ги филтрира???
        //   result = filterLastTenCreatedPosts(result,lastTen);
        return result;
    }

    @Override
    public Post getPostById(int id) {
        return posts.stream()
                .filter(post -> post.getPostId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Post", id));
    }

    //TODO Get by tag

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

    private List<Post> filterByAuthor(List<Post> posts, String username) {
        if (posts != null && username != null) {
            posts = posts.stream()
                    .filter(post -> post.getCreatedBy().getUsername().equals(username))
                    .collect(Collectors.toList());
        }
        return posts;
    }


    private List<Post> filterByDate(List<Post> posts, String date) {
        if (posts != null && date != null) {
            posts = posts.stream()
                    .filter(post -> post.getCreateDate().equals(date))
                    .collect(Collectors.toList());
        }
        return posts;
    }

    private List<Post> filterLastTenCreatedPosts(List<Post> posts, Integer lastTen) {
        lastTen = 10;
        if (posts != null) {
            posts = posts.stream().sorted(Comparator
                    .comparing(Post::getPostId)
                    .reversed()).limit(lastTen).collect(Collectors.toList());
        }
        return posts;
    }

}
