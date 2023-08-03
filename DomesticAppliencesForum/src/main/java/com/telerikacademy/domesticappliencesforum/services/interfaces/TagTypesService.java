package com.telerikacademy.domesticappliencesforum.services.interfaces;

import com.telerikacademy.domesticappliencesforum.models.TagTypes;
import com.telerikacademy.domesticappliencesforum.models.User;

import java.util.List;

public interface TagTypesService {
    List<TagTypes> get();

    TagTypes getById(int id);

    void create(TagTypes tag,User user);

    TagTypes filterByName(List<TagTypes> tags, String name);

    void delete(int id, User user);
}
