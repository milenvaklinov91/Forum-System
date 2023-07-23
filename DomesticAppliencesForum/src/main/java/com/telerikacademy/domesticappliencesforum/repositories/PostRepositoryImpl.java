package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.PostFilterOptions;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public PostRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Post> getAllPosts(PostFilterOptions filterOptions) {
        try (Session session = sessionFactory.openSession()) {
            StringBuilder stringBuilder = new StringBuilder("from Post");
            List<String> filters = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();

            filterOptions.getUsername().ifPresent(username -> {
                filters.add(" createdBy.username = :userName ");
                params.put("userName", username);
            });
            filterOptions.getLocalDate().ifPresent(date -> {
                LocalDateTime dateTime = LocalDateTime.parse(date, DateTimeFormatter.ISO_DATE_TIME);
                filters.add(" createTime = :date ");
                params.put("date", dateTime);
            });
            filterOptions.getTagId().ifPresent(tagId -> {
                filters.add(" tag.tagTypeId = :tagId ");
                params.put("tagId", tagId);
            });
            if (filterOptions.getMostComments().isPresent()) {
                stringBuilder = new StringBuilder("SELECT p FROM Post p LEFT JOIN FETCH p.comments c GROUP BY p.id ORDER BY COUNT(c) DESC");

            } else if (filterOptions.getLastTen().isPresent()) {
                stringBuilder.append(" ORDER BY postId DESC ");
            } else {
                if (!filters.isEmpty()) {
                    stringBuilder.append(" WHERE ");
                    stringBuilder.append(String.join(" AND ", filters));
                }
                stringBuilder.append(generateOrderBy(filterOptions));

            }

            Query<Post> query = session.createQuery(stringBuilder.toString(), Post.class);
            query.setProperties(params);
            return query.list();
        }

    }

    public Long countAllPosts() {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery("select count(p) from Post p where 1=1", Long.class);
            return query.getSingleResult();
        }
    }

//    public List<Post> filter(List<Post> posts, PostFilterOptions filterOptions) {
//        posts = filterByAuthor(posts, filterOptions.getUserName());
//        posts = filterByDate(posts, localDate);
//        posts = filterLastTenCreatedPosts(posts, lastTen);
//        posts = filterByTag(posts, tagId);
//        posts = filterMostCommented(mostComment);
//        return posts;
//    }
//
//    private List<Post> filterByAuthor(List<Post> posts, String username) {
//        if (posts != null && username != null) {
//            posts = posts.stream()
//                    .filter(post -> post.getCreatedBy().getUsername().equals(username))
//                    .collect(Collectors.toList());
//        }
//        return posts;
//    }
//
//    private static List<Post> filterByTag(List<Post> posts, Integer tagId) {
//        if (posts != null && tagId != null) {
//            posts = posts.stream()
//                    .filter(post -> post.getTag().getTagTypeId() == tagId)
//                    .collect(Collectors.toList());
//        }
//        return posts;
//    }
//
//
//    private List<Post> filterByDate(List<Post> posts, String date) {
//        if (posts != null && date != null) {
//            posts = posts.stream()
//                    .filter(post -> post.getCreateTime().equals(date))
//                    .collect(Collectors.toList());
//        }
//        return posts;
//    }
//
//    private List<Post> filterLastTenCreatedPosts(List<Post> posts, Integer lastTen) {
//        if (posts != null && lastTen != null) {
//            posts = posts.stream().sorted(Comparator
//                    .comparing(Post::getPostId)
//                    .reversed()).limit(10).collect(Collectors.toList());
//        }
//        return posts;
//    }
//
//    public List<Post> filterMostCommented(String mostComment) {
//        try (Session session = sessionFactory.openSession()) {
//            Query<Post> query = session.createQuery("SELECT p FROM Post p LEFT JOIN FETCH p.comments c GROUP BY p.id ORDER BY COUNT(c) DESC", Post.class);
//            query.setMaxResults(10);
//            if (mostComment != null) {
//                List<Post> result = query.list();
//                if (result.size() == 0) {
//                    throw new EntityNotFoundException("Posts", "post");
//                }
//                return result;
//            }
//            Query<Post> query1 = session.createQuery("from Post", Post.class);
//            List<Post> posts = query1.list();
//            return posts;
//        }
//    }

    public int getPostLikes(int postId) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(
                    "SELECT COUNT(v) " +
                            "FROM Vote v " +
                            "WHERE v.post.id = :post_id AND v.type.id = 1"
            );
            query.setParameter("post_id", postId);
            Long likeCount = (Long) query.uniqueResult();
            if (likeCount == 0) {
                throw new EntityNotFoundException("This post dont have likes!");
            }
            return likeCount.intValue();
        }
    }

    public int getPostDisLikes(int postId) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(
                    "SELECT COUNT(v) " +
                            "FROM Vote v " +
                            "WHERE v.post.id = :post_id AND v.type.id = 2"
            );
            query.setParameter("post_id", postId);
            Long likeCount = (Long) query.uniqueResult();
            if (likeCount == 0) {
                throw new EntityNotFoundException("This post dont have disLikes!");
            }
            return likeCount.intValue();
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
        Post postToDelete = getPostById(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(postToDelete);
            session.getTransaction().commit();
        }
    }

    private String generateOrderBy(PostFilterOptions filterOptions) {
        if (filterOptions.getSortBy().isEmpty()) {
            return "";
        }

        String orderBy = "";
        switch (filterOptions.getSortBy().get()) {
            case "username":
                orderBy = "createdBy.username";
                break;
            case "createTime":
                orderBy = "createTime";
                break;
        }

        orderBy = String.format(" ORDER BY %s", orderBy);

        if (filterOptions.getSortOrder().isPresent() && filterOptions.getSortOrder().get().equalsIgnoreCase("desc")) {
            orderBy = String.format("%s DESC", orderBy);
        }

        return orderBy;
    }
}
