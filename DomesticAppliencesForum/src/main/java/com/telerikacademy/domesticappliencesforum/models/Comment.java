package com.telerikacademy.domesticappliencesforum.models;

public class Comment {

    private int commentId = 1;
    private String content;
    private int authorId;

    //    private int like;

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
