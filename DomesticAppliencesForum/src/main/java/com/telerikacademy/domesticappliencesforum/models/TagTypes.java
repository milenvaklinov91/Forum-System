package com.telerikacademy.domesticappliencesforum.models;

import javax.persistence.*;

@Entity
@Table(name="tag_types")
public class TagTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tag_type_id")
    private int tagTypeId;
    @Column(name = "type")
    private String type;

    public TagTypes() {
    }

    public int getTagTypeId() {
        return tagTypeId;
    }

    public void setTagTypeId(int tagTypeId) {
        this.tagTypeId = tagTypeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
