package com.telerikacademy.domesticappliencesforum.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Post {

    @Positive(message = "Id should be positive")
    private int postId = 1;
    //TODO Защо трябва да го сетнем на 1 ,за да работи?

    private String title;
    private String content;
    @Positive(message = "Id should be positive")
    private User authorId;
    private LocalDate createDate;
    // private List<Comment> comments;
    // private User createdByUser;

    //TODO
    // private int like;
    //todo Трябва ли да е поле в Post или отделен Клас?

    public Post() {
    }

    public Post(String title, String content, User authorId) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.createDate = LocalDate.now();
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthorId() {
        return authorId;
    }

    public void setAuthorId(User authorId) {
        this.authorId = authorId;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

//    public List<Comment> getComments() {
//        return new ArrayList<>(comments);
//    }
//    public void setComments(List<Comment> comments) {
//        this.comments = comments;
//    }
//
//    public User getCreatedByUser() {
//        return createdByUser;
//    }
//
//    public void setCreatedByUser(User createdByUser) {
//        this.createdByUser = createdByUser;
//    }
}
