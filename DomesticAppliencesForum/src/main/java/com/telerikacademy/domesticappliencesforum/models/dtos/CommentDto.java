package com.telerikacademy.domesticappliencesforum.models.dtos;

import com.telerikacademy.domesticappliencesforum.models.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class CommentDto {
    @NotNull(message = "Content can't be empty")
    @Size(min = 4, max = 32, message = "Content should be between 32 and 8192 symbols") //todo
    private String content;

    private User createdBy;

    public CommentDto() {
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
}
