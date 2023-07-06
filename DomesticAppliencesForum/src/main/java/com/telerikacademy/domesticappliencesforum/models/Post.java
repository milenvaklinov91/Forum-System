package com.telerikacademy.domesticappliencesforum.models;

import javax.persistence.Column;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

public class Post {

    @Positive(message = "Id should be positive")
    private int postId;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Positive(message = "Id should be positive")
    private User createdBy;
    @Column(name = "create_date")
    private LocalDateTime localDateTime;

    public Post() {

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


    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
