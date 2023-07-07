package com.telerikacademy.domesticappliencesforum.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int postId;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;

    @Column(name = "create_date")
    private LocalDateTime localDateTime = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "tag_type_id")
    private TagTypes tags;



    public Post() {

    }

    public TagTypes getTags() {
        return tags;
    }

    public void setTags(TagTypes tags) {
        this.tags = tags;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
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


    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
