package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.models.TagTypes;

import java.util.List;

public interface TagTypesService {
    List<TagTypes> get();

    TagTypes get(int id);
}
