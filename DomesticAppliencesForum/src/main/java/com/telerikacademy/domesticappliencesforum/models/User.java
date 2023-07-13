package com.telerikacademy.domesticappliencesforum.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "is_admin")
    private boolean isAdmin;

//    @Column(name = "is_blocked")
//    private boolean isBlocked;
    @Column(name = "registration_date")
    private LocalDateTime registrationDate;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_login_id")
    private UserLoginDetails loginDetails;
    @JsonIgnore
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.EAGER)
    private Set<Post> post;
    @JsonIgnore
    @OneToMany(mappedBy = "createdByUser", fetch = FetchType.EAGER)
    private Set<Comment> comments;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime localDateTime) {
        this.registrationDate = localDateTime;
    }

    public UserLoginDetails getLoginDetails() {
        return loginDetails;
    }

    public void setLoginDetails(UserLoginDetails loginDetails) {
        this.loginDetails = loginDetails;
    }

    public Set<Post> getPost() {
        return post;
    }

    public void setPost(Set<Post> post) {
        this.post = post;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }
}
