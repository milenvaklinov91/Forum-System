package com.telerikacademy.domesticappliencesforum.models;

import com.telerikacademy.domesticappliencesforum.models.enums.GenderTypes;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class User {
    @Positive(message = "Id should be positive")
    private int id = 1;
    @NotNull(message = "Username can't be empty")
    @NotBlank
    private String username;
    @NotNull(message = "Name can't be empty")
    @Size(min = 4, max = 32, message = "Name should be between 4 and 32 symbols")
    @NotBlank
    private String firstName;
    @NotNull(message = "Name can't be empty")
    @Size(min = 4, max = 32, message = "Name should be between 4 and 32 symbols")
    @NotBlank
    private String lastName;
    @NotNull(message = "Email can't be empty")
    @Email(message = "Email is invalid")
    @NotBlank
    private String email;

    //TODO Как да направим валидация за password?
    @Size(min = 8, message = "Password should be between 8 symbols")
    private String password;
    private GenderTypes gender;
    private boolean isAdmin;

    public User() {

    }

    public User(String username, String firstName, String lastName,
                String email, String password, GenderTypes gender, boolean isAdmin) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.isAdmin = isAdmin;
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

    public void setGender(GenderTypes gender) {
        this.gender = gender;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
