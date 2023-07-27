package com.telerikacademy.domesticappliencesforum.repositories.interfaces;

import com.telerikacademy.domesticappliencesforum.models.*;

import java.util.List;

public interface VoteCommentRepository {

    boolean existsByCreatedByAndCommentAndVoteType(User createdBy, Comment comment, VoteTypes voteTypes);
    void save(VoteComment voteComment);

    List<VoteComment> getVoteCommentByCommentId(int commentId);

}
