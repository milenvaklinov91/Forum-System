package com.telerikacademy.domesticappliencesforum.models.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.User;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
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

    @Positive(message = "Tag type ID should be positive")
    private int tagTypeID;

    public PostDto() {
    }

    public int getTagTypeID() {
        return tagTypeID;
    }

    public void setTagTypeID(int tagTypeID) {
        this.tagTypeID = tagTypeID;
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

}