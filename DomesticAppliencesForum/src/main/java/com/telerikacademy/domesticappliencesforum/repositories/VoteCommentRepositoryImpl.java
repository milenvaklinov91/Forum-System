package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.models.*;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.VoteCommentRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class VoteCommentRepositoryImpl implements VoteCommentRepository {
    private final SessionFactory sessionFactory;
    @Autowired
    public VoteCommentRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public boolean existsByCreatedByAndCommentAndVoteType(User createdBy, Comment comment, VoteTypes voteTypes) {
        String hql = "SELECT COUNT(v) FROM VoteComment v WHERE v.createdBy = :createdBy AND v.comment = :comment AND v.typeId = :voteType";
        try (Session session = sessionFactory.openSession()) {
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("createdBy", createdBy)
                    .setParameter("comment", comment)
                    .setParameter("voteType", voteTypes)
                    .getSingleResult();
            return count > 0;
        }
    }

    public List<VoteComment> getVoteCommentByCommentId(int commentId) {
        try (Session session = sessionFactory.openSession()) {
            Query<VoteComment> query = session.createQuery("FROM VoteComment v WHERE v.comment.id = :commentId", VoteComment.class);
            query.setParameter("commentId", commentId);
            return query.getResultList();
        }
    }

    @Override
    public void save(VoteComment voteComment) {
        try (Session session = sessionFactory.openSession()) {
            session.save(voteComment);
        }
    }
}
