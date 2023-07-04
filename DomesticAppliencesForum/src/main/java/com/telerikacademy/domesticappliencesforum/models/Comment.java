package com.telerikacademy.domesticappliencesforum.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class Comment {
    @Positive(message = "Id should be positive")
    private int commentId = 1;
    private String content;
    private User createdByUser;
    private int like;

    public Comment() {
    }

    public Comment(String content, User createdByUser) {
        this.content = content;
        this.createdByUser = createdByUser;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(User createdByUser) {
        this.createdByUser = createdByUser;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
