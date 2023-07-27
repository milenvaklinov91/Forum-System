package com.telerikacademy.domesticappliencesforum.models;

import javax.persistence.*;

@Entity
@Table(name = "vote_comments")
public class VoteComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_comment_id")
    private int voteCommentId;
    @ManyToOne
    @JoinColumn(name = "vote_type_id")
    private VoteTypes typeId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;
    @ManyToOne
    private Comment comment;

    public VoteComment() {
    }

    public int getVoteCommentId() {
        return voteCommentId;
    }

    public void setVoteCommentId(int voteCommentId) {
        this.voteCommentId = voteCommentId;
    }

    public VoteTypes getTypeId() {
        return typeId;
    }

    public void setTypeId(VoteTypes typeId) {
        this.typeId = typeId;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}



