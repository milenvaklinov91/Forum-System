package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VoteRepositoryImpl implements VoteRepository {
    private final SessionFactory sessionFactory;

    public VoteRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public boolean existsByCreatedByAndPostAndVoteType(User createdBy, Post post, VoteTypes voteType) {
        String hql = "SELECT COUNT(v) FROM Vote v WHERE v.createdBy = :createdBy AND v.post = :post AND v.type = :voteType";
        try (Session session = sessionFactory.openSession()) {
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("createdBy", createdBy)
                    .setParameter("post", post)
                    .setParameter("voteType", voteType)
                    .getSingleResult();
            return count > 0;
        }
    }

    public void save(Vote vote) {
        try (Session session = sessionFactory.openSession()) {
            session.save(vote);
        }
    }
//    public List<Post> getLikedPostsByUser(int userId) {
//        try (Session session = sessionFactory.openSession()) {
//            Query query = session.createQuery("SELECT v.post FROM Vote v WHERE v.createdBy = :userId AND v.type.type = 'like'");
//            query.setParameter("createdBy", userId);
//
//            return query.list();
//        }
//    }

}
