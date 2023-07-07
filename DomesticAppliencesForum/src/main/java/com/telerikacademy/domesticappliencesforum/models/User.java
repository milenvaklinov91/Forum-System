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
    //private GenderTypes gender; //todo
    @Column(name = "is_admin")
    private boolean isAdmin;
    @Column(name = "registration_date")
    private LocalDateTime localDateTime;

    @OneToOne
    @JoinColumn(name = "user_login_id")
    private UserLoginDetails loginDetails;

    @JsonIgnore //todo
    @OneToMany(mappedBy = "createdBy", fetch = FetchType.EAGER)
    private Set<Post> post;

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


    /*public GenderTypes getGender() {
        return gender;
    }

    public void setGender(GenderTypes gender) {
        this.gender = gender;
    }*/

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
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
}
