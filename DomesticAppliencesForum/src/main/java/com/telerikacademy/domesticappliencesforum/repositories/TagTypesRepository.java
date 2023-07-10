package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.models.TagTypes;

import java.util.List;

public interface TagTypesRepository {
    List<TagTypes> get();
    TagTypes get(int id);
    void create(TagTypes tag);
    TagTypes filterByName(List<TagTypes> tags, String name);
    TagTypes getByName(String name);
    void delete(int id);
}
