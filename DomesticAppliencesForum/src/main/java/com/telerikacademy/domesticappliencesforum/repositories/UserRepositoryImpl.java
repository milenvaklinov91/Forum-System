package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserRepositoryImpl implements UserRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User", User.class);
            return query.list();
        }
    }

    public Long countAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery("select count(u) from User u where 1=1", Long.class);
            return query.getSingleResult();
        }
    }

    @Override
    public User getUserById(int id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            if (user == null) {
                throw new EntityNotFoundException("User", id);
            }
            return user;
        }
    }

    @Override
    public User getByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                    "from User user where user.username = :username", User.class);
            query.setParameter("username", username);
            List<User> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("User", "username", username);
            }
            return result.get(0);
        }
    }

    @Override
    public User getByFirstName(String firstName) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery(
                    "from User user where user.firstName = :firstName", User.class);
            query.setParameter("firstName", firstName);
            List<User> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("User", "firstName", firstName);
            }
            return result.get(0);
        }
    }
    @Override
    public User getByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where email = :email", User.class);
            query.setParameter("email", email);
            List<User> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("Email", "email", email);
            }
            return result.get(0);
        }
    }

    public List<Post> getLikedPostsByUser(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("SELECT v.post " + "FROM Vote v " +
                            "WHERE v.createdBy.id = :user_id AND v.type.id = 1");
            query.setParameter("user_id", userId);
            List<Post> likedPosts = query.list();
            return likedPosts;
        }
    }

    public List<Post> getDisLikedPostsByUser(int userId) {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery(
                    "SELECT v.post " + "FROM Vote v " +
                            "WHERE v.createdBy.id = :user_id AND v.type.id = 2");
            query.setParameter("user_id", userId);
            List<Post> likedPosts = query.list();
            return likedPosts;
        }
    }

    public void create(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(user);

            session.getTransaction().commit();
        }
    }

    public void update(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
    }

}
