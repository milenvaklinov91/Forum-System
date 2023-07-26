package com.telerikacademy.domesticappliencesforum.models;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tag_types")
public class TagTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_type_id")
    private int tagTypeId;

    @Size(min = 4, max = 32, message = "Tag name should be between 4 and 64 symbols")
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
        this.type = type.toLowerCase();
    }
}
