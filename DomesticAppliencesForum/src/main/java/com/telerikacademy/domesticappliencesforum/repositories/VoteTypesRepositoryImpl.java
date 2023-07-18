package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.TagTypes;
import com.telerikacademy.domesticappliencesforum.models.VoteTypes;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VoteTypesRepositoryImpl implements VoteTypesRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public VoteTypesRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public VoteTypes get(int id) {
        try (Session session = sessionFactory.openSession()) {
            VoteTypes voteTypes = session.get(VoteTypes.class, id);
            if (voteTypes == null) {
                throw new EntityNotFoundException("Vote type", id);
            }
            return voteTypes;
        }
    }

    public VoteTypes getByType(String type) {
        try (Session session = sessionFactory.openSession()) {
            Query<VoteTypes> query = session.createQuery("from VoteTypes  where type = :type", VoteTypes.class);
            query.setParameter("type", type);
            List<VoteTypes> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("Vote", "type", type);
            }
            return result.get(0);
        }
    }


}
