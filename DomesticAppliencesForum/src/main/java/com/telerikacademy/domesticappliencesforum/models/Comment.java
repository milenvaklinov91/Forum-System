package com.telerikacademy.domesticappliencesforum.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class Comment {
    @Positive(message = "Id should be positive")
    private int commentId = 1;
    private String content;
    private User createdByUser;
    private List<Vote> likes=new ArrayList<>();
    private Post post;
    //todo

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

    public void addLike(Vote like) {
        likes.add(like);
        like.setComment(this);
    }


    public void removeLike(Vote like) {
        likes.remove(like);
        like.setComment(null);
    }


    public List<Vote> getLikes() {
        return likes;
    }
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
