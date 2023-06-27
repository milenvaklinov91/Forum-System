package com.telerikacademy.domesticappliencesforum.models;

import com.telerikacademy.domesticappliencesforum.models.enums.GenderTypes;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Email;
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
    @Size(min = 4, max = 42, message = "Name should be between 4 and 42 symbols")
    private String firstName;
    @NotNull(message = "Name can't be empty")
    @Size(min = 4, max = 42, message = "Name should be between 4 and 42 symbols")
    private String lastName;
//TODO Къде и как трябва да излиза 'message-a' в Postman? hint:(създай един user)
    @NotNull(message = "Name can't be empty")
    @Email (message = "Email is invalid")
    private String email;
//TODO валидации за password
    private String password;

    private GenderTypes gender;

    public User() {
    }

    public User(int id, String username, String firstName, String lastName,
                String email, String password, GenderTypes gender) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gender = gender;
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

    public GenderTypes getGender() {
        return gender;
    }
//TODO Как да направим GenderType като ENUM в Postman?
    public void setGender(GenderTypes gender) {
        this.gender = gender;
    }
}
