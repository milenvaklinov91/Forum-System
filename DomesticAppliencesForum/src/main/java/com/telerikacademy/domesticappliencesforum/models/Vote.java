package com.telerikacademy.domesticappliencesforum.models;

public class Vote {
    private int voteId;
    private String type;
    private User createdBy;
    private Comment toComment;

    public Vote() {
    }

    public Vote(String type) {
        this.type = type;
    }

    public int getVoteId() {
        return voteId;
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
