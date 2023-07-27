package com.telerikacademy.domesticappliencesforum.repositories.interfaces;

import com.telerikacademy.domesticappliencesforum.models.*;

public interface VoteCommentRepository {

    boolean existsUserCreatedByAndCommentAndVoteType(User userCreatedBy, Comment comment, VoteTypes voteTypes);
    void save(VoteComment voteComment);

}
