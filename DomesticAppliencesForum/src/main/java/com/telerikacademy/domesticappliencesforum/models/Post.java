package com.telerikacademy.domesticappliencesforum.models;


import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Post {

    @Positive(message = "Id should be positive")
    private int postId = 1;
    //TODO Защо трябва да го сетнем на 1 ,за да работи?
    private String title;
    private String content;
    @Positive(message = "Id should be positive")
    private User createdBy;
    private LocalDate createDate;
    private List<Vote> likes=new ArrayList<>();;

    private List<Comment> comments;
    //todo

    public Post() {
    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
        this.createDate = LocalDate.now();
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

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }



    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }


    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }
    public void addLike(Vote like) {
        likes.add(like);
        like.setPost(this);
    }


    public void removeLike(Vote like) {
        likes.remove(like);
        like.setPost(null);
    }


    public List<Vote> getLikes() {
        return likes;
    }

}
