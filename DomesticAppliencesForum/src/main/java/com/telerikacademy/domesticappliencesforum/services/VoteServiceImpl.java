package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.mappers.VoteMapper;
import com.telerikacademy.domesticappliencesforum.models.*;
import com.telerikacademy.domesticappliencesforum.models.dtos.VoteDto;
import com.telerikacademy.domesticappliencesforum.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VoteServiceImpl implements VoteService{
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

    public void votePost(VoteDto voteDto,User userReg) {
        User user = userRepository.getUserById(voteDto.getUserId());
        Post post = postRepository.getPostById(voteDto.getPostId());
        VoteTypes type = voteTypesRepository.get(voteDto.getType());

        if (voteRepository.existsByCreatedByAndPostAndVoteType(user, post, type)) {
            throw new IllegalArgumentException("User has already voted on the post.");
        }

        Vote vote = voteMapper.fromVoteDto(voteDto, user, post, type);
        voteRepository.save(vote);
    }
    //TODO kogato user e like daden post, posle ako go dislike fda ne pravi nov zapis a da go update

    public int getVoteCountForPost(int postId) {
        return voteRepository.getLikeForPost(postId);
    }

    public int getDislikeForPost(int postId) {
        return voteRepository.getDislikeForPost(postId);
    }


}
