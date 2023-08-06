package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.PostFilterOptions;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.PostRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
            StringBuilder hqlBuilder = new StringBuilder("SELECT DISTINCT p FROM Post p");
            List<String> filters = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();

            filterOptions.getTitle().ifPresent(value -> {
                filters.add("p.title LIKE :title");
                params.put("title", String.format("%%%s%%", value));
            });
            if (filterOptions.getUsername().isPresent() && !filterOptions.getUsername().get().isEmpty()) {
                hqlBuilder.append(" LEFT JOIN p.createdBy c");
                filters.add("c.username like :username");
                params.put("username", String.format("%%%s%%", filterOptions.getUsername().get()));
            }
            filterOptions.getTagId().ifPresent(value -> {
                filters.add("p.tag like = :tag");
                params.put("tag", String.format("%%%s%%", value));
            });

            if (filterOptions.getMostComments().isPresent()) {
                hqlBuilder = new StringBuilder("SELECT p FROM Post p LEFT JOIN FETCH" +
                        " p.comments c GROUP BY p ORDER BY COUNT(c) DESC");
            } else if (filterOptions.getLastTen().isPresent()) {
                hqlBuilder.append(" ORDER BY p.postId DESC");
            } else if (filterOptions.getMostLiked().isPresent()) {
                hqlBuilder = new StringBuilder(
                        "SELECT p FROM Post p " +
                                "LEFT JOIN p.votes v " +
                                "WHERE v.type = 1 " +
                                "GROUP BY p " +
                                "ORDER BY COUNT(v) DESC"
                );
            } else {
                if (!filters.isEmpty()) {
                    hqlBuilder.append(" WHERE ");
                    hqlBuilder.append(String.join(" AND ", filters));
                }
            }

            hqlBuilder.append(generateOrderBy(filterOptions));

            Query<Post> query = session.createQuery(hqlBuilder.toString(), Post.class);
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

    public List<Post> getLastTenCreatedPosts() {
        try (Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery("SELECT p FROM Post p ORDER BY p.postId DESC", Post.class);
            query.setMaxResults(10);
            List<Post> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("Posts", "post");
            }
            return result;
        }
    }

    public List<Post> getMostCommented() {
        try (Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery("SELECT p FROM Post p LEFT JOIN FETCH p.comments c GROUP BY p.id ORDER BY COUNT(c) DESC", Post.class);
            query.setMaxResults(10);
            List<Post> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("Posts", "post");
            }
            return result;
        }
    }

    public List<Post> getMostLiked() {
        try (Session session = sessionFactory.openSession()) {
            Query<Post> query = session.createQuery("SELECT p FROM Post p " +
                    "LEFT JOIN p.votes v " +
                    "WHERE v.type ='1' " +
                    "GROUP BY p " +
                    "ORDER BY COUNT(v) DESC", Post.class);
            query.setMaxResults(10);
                List<Post> result = query.list();
                if (result.size() == 0) {
                    throw new EntityNotFoundException("Posts", "post");
                }
                return result;
            }
    }


    public int getPostLikes(int postId) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(
                    "SELECT COUNT(v) " +
                            "FROM Vote v " +
                            "WHERE v.post.id = :post_id AND v.type.id = 1"
            );
            query.setParameter("post_id", postId);
            Long likeCount = (Long) query.uniqueResult();
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
            case "title":
                orderBy = "title";
                break;
            case "username":
                orderBy = "username";
                break;
            case "createTime":
                orderBy = "p.createTime";
                break;
            case "tagTypeId":
                orderBy = "p.tag.tagTypeId";
                break;
            case "mostComments":
                orderBy = "p.comments.size DESC";
                break;
            case "lastTen":
                orderBy = "p.postId";
                break;
            case "mostLiked":
                orderBy = "p.votes";
                break;
            default:
                orderBy = "p.postId";
        }

        orderBy = String.format(" ORDER BY %s ", orderBy);

        if (filterOptions.getSortOrder().isPresent() && filterOptions.
                getSortOrder().get().equalsIgnoreCase("desc")) {
            orderBy = String.format(" %s desc ", orderBy);
        }

        return orderBy;
    }
}

