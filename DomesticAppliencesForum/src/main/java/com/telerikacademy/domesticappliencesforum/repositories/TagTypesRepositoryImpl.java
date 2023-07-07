package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.TagTypes;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TagTypesRepositoryImpl implements TagTypesRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public TagTypesRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<TagTypes> get() {
        try (Session session = sessionFactory.openSession()) {
            Query<TagTypes> query = session.createQuery("from TagTypes ", TagTypes.class);
            return query.list();
        }
    }

    @Override
    public TagTypes get(int id) {
        try (Session session = sessionFactory.openSession()) {
            TagTypes tagTypes = session.get(TagTypes.class, id);
            if (tagTypes == null) {
                throw new EntityNotFoundException("Tag type", id);
            }
            return tagTypes;
        }
    }
}
