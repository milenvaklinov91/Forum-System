package com.telerikacademy.domesticappliencesforum.models;

import java.text.DateFormat;
import java.time.LocalDate;

public class Post {
    private int id;
    private String title;
    private String content;
    private String comment;
    private User createdBy;
    private LocalDate createDate;

    //TODO
   // private int like;


    public Post() {
    }

    public Post(String title, String content, User createdBy, LocalDate createDate) {
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        createDate = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

}
