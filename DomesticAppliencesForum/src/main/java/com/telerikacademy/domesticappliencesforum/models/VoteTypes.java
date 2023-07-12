package com.telerikacademy.domesticappliencesforum.models;

import javax.persistence.*;

@Entity
@Table(name="vote_types")
public class VoteTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int voteTypeID;
    @Column(name="type")
    private String type;

    public VoteTypes() {
    }

    public int getVoteTypeID() {
        return voteTypeID;
    }

    public void setVoteTypeID(int voteTypeID) {
        this.voteTypeID = voteTypeID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
