package com.telerikacademy.domesticappliencesforum.services;

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

    }
}
