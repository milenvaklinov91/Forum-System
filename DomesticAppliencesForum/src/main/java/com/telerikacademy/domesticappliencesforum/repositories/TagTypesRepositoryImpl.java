package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.TagTypes;
import com.telerikacademy.domesticappliencesforum.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

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

    public void create(TagTypes tag) {
        try (Session session = sessionFactory.openSession()) {
            session.save(tag);
        }
    }

    public TagTypes filterByName(List<TagTypes> tags, String name) {
        if (tags != null && name != null) {
            tags = tags.stream()
                    .filter(tag -> tag.getType().equals(name))
                    .collect(Collectors.toList());
        }
        return tags.get(0);
    }

    public TagTypes getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            Query<TagTypes> query = session.createQuery("from TagTypes  where type = :name", TagTypes.class);
            query.setParameter("name", name);
            List<TagTypes> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("Tag", "type", name);
            }
            return result.get(0);
        }
    }

    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(get(id));
            session.getTransaction().commit();
        }

    }

}
