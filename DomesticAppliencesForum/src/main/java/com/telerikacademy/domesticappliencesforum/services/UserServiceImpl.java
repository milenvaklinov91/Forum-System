package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.exceptions.DuplicatePasswordException;
import com.telerikacademy.domesticappliencesforum.exceptions.EmailExitsException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityDuplicateException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
    this.repository = repository;
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public User getById(int id) {
        return repository.getUserById(id);
    }

    public User getByUsername(String username) {
        return repository.getByUsername(username);
    }

    public void create(User user) {
        isDuplicateUsername(user);
        isDuplicateEmail(user);
        user.setRegistration(LocalDateTime.now()); //todo
        repository.create(user);
    }

    private void isDuplicateUsername(User user) {
        boolean duplicateExists = true;
        try {
            repository.getByUsername(user.getLoginDetails().getUsername());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new EntityDuplicateException("User", user.getLoginDetails().getUsername());
        }
    }

    private void isDuplicateEmail(User user) {
        boolean duplicateEmail = true;
        try {
            repository.getByEmail(user.getEmail());
        } catch (EntityNotFoundException e) {
            duplicateEmail = false;
        }
        if (duplicateEmail) {
            throw new EmailExitsException(user.getEmail());
        }
    }

    public void update(User user) {
        boolean duplicatePass = true;
        try {
            repository.getByPassword(user.getId(), user.getLoginDetails().getPassword());
        } catch (DuplicatePasswordException e) {
            duplicatePass = false;
        }
        if (duplicatePass) {
            throw new DuplicatePasswordException(user.getLoginDetails().getPassword());
        }
        repository.update(user);
    }

    public void delete(int id) {
        repository.delete(id);
    }
}
