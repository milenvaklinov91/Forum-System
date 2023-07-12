package com.telerikacademy.domesticappliencesforum.models.dtos;

public class VoteDto {
    private int userId;
    private int postId;
    private int type;

    public VoteDto() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
