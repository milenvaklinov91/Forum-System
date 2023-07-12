package com.telerikacademy.domesticappliencesforum.models.dtos;

import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class CommentDto {
    @NotNull(message = "Content can't be empty")
    @Size(min = 32, max = 8192, message = "Content should be between 32 and 8192 symbols")
    private String content;

    private int postId;

    public CommentDto() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
