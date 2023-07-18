package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.models.VoteTypes;

public interface VoteTypesRepository {
    VoteTypes get(int id);

    VoteTypes getByType(String type);

}
