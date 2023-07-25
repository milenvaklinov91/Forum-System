package com.telerikacademy.domesticappliencesforum.services.interfaces;

import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.Vote;
import com.telerikacademy.domesticappliencesforum.models.dtos.VoteDto;

import java.util.List;

public interface VoteService {
  void votePost(Vote vote, User userReg);

}
