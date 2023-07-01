package com.telerikacademy.domesticappliencesforum.models.dtos;

import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class PostDto {
    @NotNull(message = "Title can't be empty")
    @Size(min = 4, max = 32, message = "Title should be between 16 and 64 symbols")
    private String title;
    @NotNull(message = "Content can't be empty")
    @Size(min = 4, max = 32, message = "Content should be between 32 and 8192 symbols")
    private String content;
    //TODO
    private int authorId;
    private User createdByUser;
    private LocalDate createDate;

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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
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

    public User getCreatedByUser() {
        return createdByUser;
    }

    public void setCreatedByUser(User createdByUser) {
        this.createdByUser = createdByUser;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }
}
