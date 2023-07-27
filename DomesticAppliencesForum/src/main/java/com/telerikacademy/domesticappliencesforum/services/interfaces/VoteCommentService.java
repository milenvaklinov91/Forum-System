package com.telerikacademy.domesticappliencesforum.services.interfaces;

import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.VoteComment;

import java.util.List;

public interface VoteCommentService {

    void voteComment(VoteComment voteComment , User userReg);
    List<VoteComment> getVoteCommentByCommentId(int commentId);

}
