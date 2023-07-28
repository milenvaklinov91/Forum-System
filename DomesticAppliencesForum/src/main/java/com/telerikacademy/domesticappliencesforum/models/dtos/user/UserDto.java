package com.telerikacademy.domesticappliencesforum.models.dtos.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto {
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
    @NotBlank
    @Size(min = 8, message = "Password should minimum 8 symbols")
    private String password;

    public UserDto() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
