package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.FilterOptionsComment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public CommentRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

//    @Override
//    public List<Comment> getAllComments(FilterOptionsComment filterOptionsComment) {
//        try (Session session = sessionFactory.openSession()) {
//            Query<Comment> query = session.createQuery("from Comment", Comment.class);
//            List<Comment> comments = query.list();
//            return filter(comments, username, localDate, vote);
//        }
//    }

    @Override
    public List<Comment> getAllComments(FilterOptionsComment filterOptionsComment) {
        try (Session session = sessionFactory.openSession()) {
            StringBuilder stringBuilder = new StringBuilder("from Comments");
            List<String> filters = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();

            filterOptionsComment.getUserId().ifPresent(value -> {
                filters.add("user_id like :userId");
                params.put("userId",value);
            });

            StringBuilder queryString = new StringBuilder("from Comment");
            if (!filters.isEmpty()) {
                queryString
                        .append(" where ")
                        .append(String.join(" and ", filters));
            }
            System.out.println(queryString);
            Query<Comment> query = session.createQuery(queryString.toString(), Comment.class);
            query.setProperties(params);
            return query.list();
        }
    }


    @Override
    public void create(Comment comment) {
        try (Session session = sessionFactory.openSession()) {
            session.save(comment);
        }
    }

    @Override
    public void modify(Comment comment) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(comment);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        Comment commentToDelete = getCommentById(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(commentToDelete);
            session.getTransaction().commit();
        }
    }



    @Override
    public Comment getCommentById(int id) {
        try (Session session = sessionFactory.openSession()) {
            Comment comment = session.get(Comment.class, id);
            if (comment == null) {
                throw new EntityNotFoundException("Comment", id);
            }
            return comment;
        }
    }

    public List<Comment> filter(List<Comment> comments, String username, String localDate, Integer vote) {
        comments = filterCommentsByAuthor(comments, username);
        comments = filterCommentsByDate(comments, localDate);
        comments = filterCommentsByMostLiked(comments, vote);
        return comments;
        //todo за филтър виж лекцията с Пешо и демото
    }

    private List<Comment> filterCommentsByDate(List<Comment> comments, String date) {
        if (comments != null && date != null) {
            comments = comments.stream()
                    .filter(comment -> comment.getCreateTime().equals(date))
                    .collect(Collectors.toList());
        }
        return comments;
    }

    private List<Comment> filterCommentsByAuthor(List<Comment> comments, String username) {
        if (comments != null && username != null) {
            comments = comments.stream()
                    .filter(comment -> comment.getCreatedByUser().equals(username))
                    .collect(Collectors.toList());
        }
        return comments;
    }

    private List<Comment> filterCommentsByMostLiked(List<Comment> comments, Integer vote) {
//        if (comments != null && vote != null) {
//            comments = comments.stream()
//                    .filter(comment -> comment.getVote() >= vote)
//                    .collect(Collectors.toList());
//        }
        return comments;
    }
}
