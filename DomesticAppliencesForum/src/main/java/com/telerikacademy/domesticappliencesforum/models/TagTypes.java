package com.telerikacademy.domesticappliencesforum.models;

public class TagTypes {

    private int tagTypeId;
    private Tag tagID;
    private String type;

    public TagTypes() {
    }

    public int getTagTypeId() {
        return tagTypeId;
    }

    public void setTagTypeId(int tagTypeId) {
        this.tagTypeId = tagTypeId;
    }

    public Tag getTagID() {
        return tagID;
    }

    public void setTagID(Tag tagID) {
        this.tagID = tagID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
