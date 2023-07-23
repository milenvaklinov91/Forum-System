package com.telerikacademy.domesticappliencesforum.services.interfaces;

import com.telerikacademy.domesticappliencesforum.models.TagTypes;
import com.telerikacademy.domesticappliencesforum.models.User;

import java.util.List;

public interface TagTypesService {
    List<TagTypes> get();

    TagTypes get(int id);

    void create(TagTypes tag);

    TagTypes filterByName(List<TagTypes> tags, String name);

    void delete(int id, User user);
}
