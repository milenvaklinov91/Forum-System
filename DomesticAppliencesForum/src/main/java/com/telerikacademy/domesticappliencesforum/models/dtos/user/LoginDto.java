package com.telerikacademy.domesticappliencesforum.models.dtos.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class LoginDto {

    @NotEmpty(message = "Username can't be empty")
    private String username;

    //@NotEmpty(message = "Password can't be empty")
    @Size(min = 8, message = "Password should be minimum 8 symbols")
    private String password;

    public LoginDto() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}


