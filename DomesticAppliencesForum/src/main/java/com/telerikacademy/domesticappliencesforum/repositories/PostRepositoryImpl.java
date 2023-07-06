package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public PostRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Post> getAllPosts(String authorId, String localDate, Integer lastTen) {
        try (Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery("from Post", Post.class);
            List<Post> posts = query.list();
            return filter(posts, authorId, localDate, lastTen);
        }

    }

    @Override
    public Post getPostById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Post post = session.get(Post.class, id);
            if (post == null) {
                throw new EntityNotFoundException("Post", id);
            }
            return post;

        }
    }

    //TODO Get by tag

    @Override
    public void create(Post post) {
        try (Session session = sessionFactory.openSession()) {
            session.save(post);
        }
    }

    @Override
    public void modify(Post post) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(post);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        Post postToDelete=getPostById(id);
        try(Session session=sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(postToDelete);
            session.getTransaction().commit();
        }
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
                    .filter(post -> post.getLocalDateTime().equals(date))
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

    public List<Post> filter(List<Post> posts, String authorId, String localDate, Integer lastTen) {
        posts = filterByAuthor(posts, authorId);
        posts = filterByDate(posts, localDate);
        posts = filterLastTenCreatedPosts(posts, lastTen);
        return posts;
    }

}
