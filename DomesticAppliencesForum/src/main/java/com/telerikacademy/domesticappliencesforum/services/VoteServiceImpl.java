package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.exceptions.UnauthorizedOperationException;
import com.telerikacademy.domesticappliencesforum.mappers.VoteMapper;
import com.telerikacademy.domesticappliencesforum.models.*;
import com.telerikacademy.domesticappliencesforum.models.dtos.VoteDto;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.PostRepository;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.UserRepository;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.VoteRepository;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.VoteTypesRepository;
import com.telerikacademy.domesticappliencesforum.services.interfaces.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final VoteMapper voteMapper;
    private final VoteTypesRepository voteTypesRepository;

    @Autowired
    public VoteServiceImpl(VoteRepository voteRepository, UserRepository userRepository,
                           PostRepository postRepository, VoteMapper voteMapper,
                           VoteTypesRepository voteTypesRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.voteMapper = voteMapper;
        this.voteTypesRepository = voteTypesRepository;
    }


    public void votePost(Vote vote, User userReg) {
        vote.setCreatedBy(userReg);
        Post post = postRepository.getPostById(vote.getPost().getPostId());
        VoteTypes type = voteTypesRepository.get(vote.getType().getVoteTypeID());
        if (voteRepository.existsByCreatedByAndPostAndVoteType(userReg, post, type)) {
            throw new IllegalArgumentException("User has already voted on the post.");
        }else if(userReg.isBlocked()){
            throw new UnauthorizedOperationException("You`re blocked!!!");
        }
        voteRepository.save(vote);
    }

}
