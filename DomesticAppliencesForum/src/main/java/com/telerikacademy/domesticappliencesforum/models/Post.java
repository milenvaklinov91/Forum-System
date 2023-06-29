package com.telerikacademy.domesticappliencesforum.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.time.LocalDate;

public class Post {
    @Positive(message = "Id should be positive")
    private int id = 1;
    //TODO Защо трябва да го сетнем на 1 ,за да работи?

    @NotNull(message = "Title can't be empty")
    @Size(min = 4, max = 32, message = "Title should be between 16 and 64 symbols")
    private String title;
    @NotNull(message = "Content can't be empty")
    @Size(min = 4, max = 32, message = "Content should be between 32 and 8192 symbols")
    private String content;
    //TODO
    //private String comment;
    //todo Трябва ли да е поле в Post или отделен Клас?
    private int authorId;
    private LocalDate createDate;

    //TODO
   // private int like;
    //todo Трябва ли да е поле в Post или отделен Клас?


    public Post() {
    }

    public Post(String title, String content, int authorId, LocalDate createDate) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.createDate = LocalDate.now();
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


    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

}
