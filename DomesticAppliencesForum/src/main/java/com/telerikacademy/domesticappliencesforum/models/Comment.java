package com.telerikacademy.domesticappliencesforum.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class Comment {
    @Positive(message = "Id should be positive")
    private int commentId = 1;
    @NotNull(message = "Content can't be empty")
    @Size(min = 4, max = 32, message = "Content should be between 32 and 8192 symbols")
    private String content;
    @Positive(message = "Id should be positive")
    private int authorId;

    public Comment() {
    }

    public Comment(String content, int authorId) {
        this.content = content;
        this.authorId = authorId;
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

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
