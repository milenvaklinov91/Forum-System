package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.exceptions.DuplicatePasswordException;
import com.telerikacademy.domesticappliencesforum.exceptions.EmailExitsException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.enums.GenderTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private final List<User> users;

    private int id = 1;
    @Autowired
    public UserRepositoryImpl() {
        users = new ArrayList<>();

        User user1 = new User("milenvaklinov", "milen",
                "vaklinov", "milen91@abv.bg", "milen91", GenderTypes.Male,true);
        user1.setId(id++);
        users.add(user1);
        User user2 = new User("ledayovkova", "leda",
                "yovkova", "leda@abv.bg", "leda123", GenderTypes.Female,false);
        user2.setId(id++);
        users.add(user2);
    }
    @Override
    public List<User> getAll() {
        return new ArrayList<>(users);
    }
    @Override
    public User getUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("User", id));
    }
    @Override
    public User getByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("User", username));

    }
    @Override
    public User getByPassword(int id, String password) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .filter(user -> user.getPassword().equals(password))
                .findFirst()
                .orElseThrow(() -> new DuplicatePasswordException(password));
    }

    public User getByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new EmailExitsException(email));
    }

    public void create(User user) {
        user.setId(id++);
        users.add(user);
    }

    public void update(User user) {
        User userToUpdate = getUserById(user.getId());
        userToUpdate.setPassword(user.getPassword());
    }

    public void delete(int id) {
        User userToDelete = getUserById(id);
        users.remove(userToDelete);
    }

}
