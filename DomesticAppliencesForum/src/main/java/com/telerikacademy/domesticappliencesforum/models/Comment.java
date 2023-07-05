package com.telerikacademy.domesticappliencesforum.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

//@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;
    // @Column(name = "content")
    private String comment;
    private User createdByUser;
    private List<Vote> likes = new ArrayList<>();
    private Post post;
    //todo

    public Comment() {
    }

    public Comment(String comment, User createdByUser) {
        this.comment = comment;
        this.createdByUser = createdByUser;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
