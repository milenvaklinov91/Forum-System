package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.models.*;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.VoteRepository;
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
    public List<Vote> getVotesByPostId(int postId) {
        try (Session session = sessionFactory.openSession()) {
            Query<Vote> query = session.createQuery("FROM Vote v WHERE v.post.id = :postId", Vote.class);
            query.setParameter("postId", postId);
            return query.getResultList();
        }
    }

    public void save(Vote vote) {
        try (Session session = sessionFactory.openSession()) {
            session.save(vote);
        }
    }

}
