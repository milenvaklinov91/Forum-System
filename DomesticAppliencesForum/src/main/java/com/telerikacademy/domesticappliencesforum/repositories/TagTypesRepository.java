package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.models.TagTypes;

import java.util.List;

public interface TagTypesRepository {
    List<TagTypes> get();
    TagTypes get(int id);
}
