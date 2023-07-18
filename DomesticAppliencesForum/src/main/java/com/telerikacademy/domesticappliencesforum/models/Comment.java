package com.telerikacademy.domesticappliencesforum.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;
    @Column(name = "comment")
    private String comment;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdByUser;
    @Column(name = "create_date")
    private LocalDateTime createTime = LocalDateTime.now();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post postId;

    public Comment() {

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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime localDateTime) {
        this.createTime = localDateTime;
    }

    public Post getPostId() {
        return postId;
    }

    public void setPostId(Post postId) {
        this.postId = postId;
    }
}
