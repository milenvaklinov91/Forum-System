package com.telerikacademy.domesticappliencesforum.models.dtos;

import com.telerikacademy.domesticappliencesforum.models.enums.GenderTypes;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto {
    @NotNull(message = "Username can't be empty")
    private String username;
    @NotNull(message = "Email can't be empty")
    @Email(message = "Email is invalid")
    private String email;
    private GenderTypes gender;

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

    public GenderTypes getGender() {
        return gender;
    }

    public void setGender(GenderTypes gender) {
        this.gender = gender;
    }
}
