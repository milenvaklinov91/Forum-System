package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.dtos.VoteDto;

public interface VoteService {
    void votePost(VoteDto voteDto, User userReg);
}
