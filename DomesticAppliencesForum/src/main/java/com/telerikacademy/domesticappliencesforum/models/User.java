package com.telerikacademy.domesticappliencesforum.models;

import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class User {
    @Positive(message = "Id should be positive" )
    private int id;
    @NotNull(message = "Name can't be empty")
    @Size(min = 5, max = 45, message = "Name should be between 5 and 45 symbols")
    private String username;
    @NotNull( message = "Name can't be empty")
    @Size(min = 5, max = 45, message = "Name should be between 5 and 45 symbols")
    private String firstName;
    @NotNull(message = "Name can't be empty")
    @Size(min = 5, max = 45, message = "Name should be between 5 and 45 symbols")
    private String lastName;
    @NotNull(message = "Name can't be empty")
    private String email;
    private String password;

    public User() {
    }

    public User(int id, String username, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
