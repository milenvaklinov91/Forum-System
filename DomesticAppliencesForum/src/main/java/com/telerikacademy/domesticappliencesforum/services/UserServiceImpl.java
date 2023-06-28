package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.exceptions.DuplicatePasswordException;
import com.telerikacademy.domesticappliencesforum.exceptions.EmailExitsException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityDuplicateException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.repositories.UserRepository;
import com.telerikacademy.domesticappliencesforum.repositories.UserRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserRepositoryImpl repository;

    public UserServiceImpl() {
        this.repository = new UserRepositoryImpl();
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public User getById(int id) {
        return repository.getUserById(id);
    }

    public void create(User user) {
        boolean duplicateExists = true;
        try {
            repository.getByUsername(user.getUsername());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new EntityDuplicateException("User", user.getUsername());
        }
        boolean duplicateEmail = true;
        try {
            repository.getByEmail(user.getEmail());
        } catch (EmailExitsException e) {
            duplicateEmail = false;
        }
        if (duplicateEmail) {
            throw new EmailExitsException(user.getEmail());
        }
        repository.create(user);
    }
//TODO Можем ли да добавим всички exception-ни относно създаване на user(username, email), чрез try и catch както е посочено горе?

    public void update(User user) {
        boolean duplicatePass = true;
        try {
            repository.getByPassword(user.getId(), user.getPassword());
        } catch (DuplicatePasswordException e) {
            duplicatePass = false;
        }
        if (duplicatePass) {
            throw new DuplicatePasswordException(user.getPassword());
        }
        repository.update(user);
    }

    public void delete(int id) {
        repository.delete(id);
    }
}
