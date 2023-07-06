package com.telerikacademy.domesticappliencesforum.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public class PostDto {
    @NotNull(message = "Title can't be empty")
    @Size(min = 4, max = 32, message = "Title should be between 16 and 64 symbols")
    private String title;
    @NotNull(message = "Content can't be empty")
    @Size(min = 32, max = 8192, message = "Content should be between 32 and 8192 symbols")
    private String content;
//    @JsonIgnore
//    @NotNull(message = "You need to be user to create a post")
//    private User createdBy;
    @JsonIgnore
    private LocalDateTime localDateTime=LocalDateTime.now();

    public PostDto() {
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


//    public User getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(User createdBy) {
//        this.createdBy = createdBy;
//    }

}