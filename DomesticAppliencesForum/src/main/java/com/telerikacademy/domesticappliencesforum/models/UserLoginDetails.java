package com.telerikacademy.domesticappliencesforum.models;

import javax.persistence.*;

@Entity
@Table(name = "user_login")
public class UserLoginDetails {
//todo
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_login_id")
    private int userLoginId;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    public UserLoginDetails() {
    }

    public int getUserLoginId() {
        return userLoginId;
    }

    public void setUserLoginId(int userLoginId) {
        this.userLoginId = userLoginId;
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
