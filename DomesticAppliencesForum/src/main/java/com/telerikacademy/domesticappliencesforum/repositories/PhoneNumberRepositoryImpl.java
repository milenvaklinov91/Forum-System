package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.PhoneNumber;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.PhoneNumberRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PhoneNumberRepositoryImpl implements PhoneNumberRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public PhoneNumberRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public PhoneNumber getPhoneNumberById(int id) {
        try (Session session = sessionFactory.openSession()) {
            PhoneNumber phoneNumber = session.get(PhoneNumber.class, id);
            if (phoneNumber == null) {
                throw new EntityNotFoundException("PhoneNumber", id);
            }
            return phoneNumber;
        }
    }

    @Override
    public void createPhoneNumber(PhoneNumber phoneNumber){
        try (Session session = sessionFactory.openSession()) {
            session.save(phoneNumber);
        }
    }
}
