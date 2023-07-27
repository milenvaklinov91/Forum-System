package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.VoteComment;
import com.telerikacademy.domesticappliencesforum.models.VoteTypes;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.VoteCommentRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class VoteCommentRepositoryImpl implements VoteCommentRepository {
    private final SessionFactory sessionFactory;

    public VoteCommentRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public boolean existsUserCreatedByAndCommentAndVoteType(User userCreatedBy, Comment comment, VoteTypes voteTypes) {
        String hql = "SELECT COUNT(v) FROM VoteComment v WHERE v.createdBy = :createdBy AND v.comment = :comment AND v.typeId = :voteType";
        try (Session session = sessionFactory.openSession()) {
            Long count = session.createQuery(hql, Long.class)
                    .setParameter("createdBy", userCreatedBy)
                    .setParameter("comment", comment)
                    .setParameter("voteType", voteTypes)
                    .getSingleResult();
            return count > 0;
        }
    }

    @Override
    public void save(VoteComment voteComment) {
        try (Session session = sessionFactory.openSession()) {
            session.save(voteComment);
        }
    }
}
