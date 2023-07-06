package com.telerikacademy.domesticappliencesforum.models;

import javax.persistence.Column;

public class Vote {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int voteId;
 //   @Column(name = "type")
    private String type;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
    private User createdBy;
    //TODO Да добавим колона постове в базата, за да вържем харесвания и на пост

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "post_id")
    private Post post;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "comment_id")
    private Comment comment;


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

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }



    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
