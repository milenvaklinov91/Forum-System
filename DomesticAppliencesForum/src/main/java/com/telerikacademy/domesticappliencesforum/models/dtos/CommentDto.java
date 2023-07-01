package com.telerikacademy.domesticappliencesforum.models.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class CommentDto {
    @NotNull(message = "Content can't be empty")
    @Size(min = 4, max = 32, message = "Content should be between 32 and 8192 symbols")
    private String content;
    @Positive(message = "Id should be positive")
    private int authorId;

    public CommentDto() {
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
}
