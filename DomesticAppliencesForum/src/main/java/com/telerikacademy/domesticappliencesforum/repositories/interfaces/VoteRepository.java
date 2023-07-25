package com.telerikacademy.domesticappliencesforum.repositories.interfaces;

import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.Vote;
import com.telerikacademy.domesticappliencesforum.models.VoteTypes;

public interface VoteRepository {
    boolean existsByCreatedByAndPostAndVoteType(User createdBy, Post post, VoteTypes voteType);

    void save(Vote vote);

}
