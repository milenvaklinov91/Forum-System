package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.exceptions.*;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.UserRepository;
import com.telerikacademy.domesticappliencesforum.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAll() {        //admin
        return repository.getAll();
    }

    public Long countAllUsers() {
        return repository.countAllUsers();
    }

    public User getById(int id) {   //admin or ownuser
        return repository.getUserById(id);
    }

    public User getByUsername(String username) {
        return repository.getByUsername(username);
    }

    public User getByFirstName(String firstName) {
        return repository.getByFirstName(firstName);
    }

    public User getByEmail(String email) {
        return repository.getByEmail(email);
    }

    public List<Post> getLikedPostsByUser(int userId) {
        List<Post> allPosts = repository.getLikedPostsByUser(userId);
        if (allPosts.isEmpty()) {
            throw new EntityNotFoundException("This user dont have liked posts!");
        }
        return repository.getLikedPostsByUser(userId);
    }

    public List<Post> getDisLikedPostsByUser(int userId) {
        List<Post> allPosts = repository.getDisLikedPostsByUser(userId);
        if (allPosts.isEmpty()) {
            throw new EntityNotFoundException("This user dont have disliked posts!");
        }
        return repository.getDisLikedPostsByUser(userId);
    }

    public void create(User user) {
        isDuplicateUsername(user);
        isDuplicateEmail(user);
        user.setRegistrationDate(LocalDateTime.now());
        repository.create(user);
    }

    private void isDuplicateUsername(User user) {
        boolean duplicateExists = true;
        try {
            repository.getByUsername(user.getUsername());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }
        if (duplicateExists) {
            throw new EntityDuplicateException("User", user.getUsername());
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

    public void update(User loggedUser, User user) {
        try {
            User existUser = repository.getUserById(user.getId());

            existUser.setFirstName(user.getFirstName());
            existUser.setLastName(user.getLastName());
            existUser.setEmail(user.getEmail());
            existUser.setPassword(user.getPassword());

            if (!existUser.getUsername().equals(user.getUsername())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "You cannot update username!");
            }

            if (user.isBlocked()) {
                existUser.setBlocked(true);
            }
            if (user.isAdmin()) {
                existUser.setAdmin(true);
            }

            repository.update(existUser);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    public User getUserDetails(int id, User user) {
        User admin = repository.getUserById(id);
        if (!admin.isAdmin()) {
            throw new UnauthorizedOperationException("You're not authorized for this operation!");
        }
        return admin;
    }

    public User blockUser(int id, User user) {
        User normalUser = repository.getUserById(id);
        if (user.isAdmin() && !normalUser.isAdmin()) {
            normalUser.setBlocked(true);
            repository.update(normalUser);
            return normalUser;
        }
        throw new UnauthorizedOperationException("You're not authorized for this operation!");
    }

    public User unBlockUser(int id, User user) {
        User normalUser = repository.getUserById(id);
        if (user.isAdmin() && !normalUser.isAdmin()) {
            normalUser.setBlocked(false);
            repository.update(normalUser);
            return normalUser;
        }
        throw new UnauthorizedOperationException("You're not authorized for this operation!");
    }

    public User makeAdmin(int id, User user) {
        User user1 = repository.getUserById(id);
        if (user.isAdmin() && !user1.isAdmin()) {
            user1.setAdmin(true);
            repository.update(user1);
            return user1;
        }
        throw new UnauthorizedOperationException("You're not authorized for this operation!");
    }

    public User demoteAdmin(int id, User user) {
        User user1 = repository.getUserById(id);
        if (user.isAdmin() && user1.isAdmin()) {
            user1.setAdmin(false);
            repository.update(user1);
            return user1;
        }
        throw new UnauthorizedOperationException("You're not authorized for this operation!");
    }

}
