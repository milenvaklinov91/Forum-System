package com.telerikacademy.domesticappliencesforum.models.dtos;

public class VoteCommentDto {

    private int userId;
    private int commentId;
    private int type;

    public VoteCommentDto() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
