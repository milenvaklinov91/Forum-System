package com.telerikacademy.domesticappliencesforum.models;

public class VoteTypes {

    private int voteTypeID;
    private Vote voteID;
    private String type;

    public VoteTypes() {
    }

    public int getVoteTypeID() {
        return voteTypeID;
    }

    public void setVoteTypeID(int voteTypeID) {
        this.voteTypeID = voteTypeID;
    }

    public Vote getVoteID() {
        return voteID;
    }

    public void setVoteID(Vote voteID) {
        this.voteID = voteID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
