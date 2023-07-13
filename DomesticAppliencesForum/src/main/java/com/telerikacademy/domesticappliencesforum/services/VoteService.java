package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.dtos.VoteDto;

import java.util.List;

public interface VoteService {
    void votePost(VoteDto voteDto, User userReg);
}
