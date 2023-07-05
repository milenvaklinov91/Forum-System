package com.telerikacademy.domesticappliencesforum.models;


public class Tag {
    //    @Id
    //    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tagId;

    //  @Column(name = "type")
    private String type;

    //    @ManyToOne(fetch = FetchType.LAZY)
    //    @JoinColumn(name = "user_id")
    private User createdBy;

    //    @ManyToOne(fetch = FetchType.LAZY)
    //    @JoinColumn(name = "post_id")
    private Post post;

    public Tag() {
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
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
}
