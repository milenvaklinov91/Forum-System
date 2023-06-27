package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityDuplicateException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.repositories.UserRepositoryImpl;

import java.util.List;

public class UserServiceImpl {

    private UserRepositoryImpl repository;

    public UserServiceImpl() {
        this.repository = new UserRepositoryImpl();
    }

    public List<User> getAll(){
        return repository.getAll();
    }
    public User getById(int id){
        return repository.getUserById(id);
    }
    public void create(User user){
        boolean duplicateExists = true;
        try {
            repository.getByUsername(user.getUsername());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (duplicateExists) {
            throw new EntityDuplicateException("User", user.getUsername());
        }

        repository.create(user);
    }

    public void update(User user,String newPassword) {
        repository.update(user,newPassword);
    }

    public void delete(int id) {
        repository.delete(id);
    }
}
