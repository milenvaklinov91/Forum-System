package com.telerikacademy.domesticappliencesforum.mappers;

import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.Vote;
import com.telerikacademy.domesticappliencesforum.models.VoteTypes;
import com.telerikacademy.domesticappliencesforum.models.dtos.VoteDto;
import com.telerikacademy.domesticappliencesforum.repositories.PostRepository;
import com.telerikacademy.domesticappliencesforum.repositories.UserRepository;
import com.telerikacademy.domesticappliencesforum.repositories.VoteTypesRepository;
import com.telerikacademy.domesticappliencesforum.services.PostService;
import org.springframework.stereotype.Component;

@Component
public class VoteMapper {

    private VoteTypesRepository voteTypesRepository;
    private PostService postService;

    private UserRepository userRepository;
    private PostRepository postRepository;

    public VoteMapper(VoteTypesRepository voteTypesRepository, PostService postService, UserRepository userRepository, PostRepository postRepository) {
        this.voteTypesRepository = voteTypesRepository;
        this.postService = postService;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public VoteDto fromDto(Vote vote) {
        VoteDto dto = new VoteDto();
        dto.setUserId(vote.getCreatedBy().getId());
        dto.setPostId(vote.getPost().getPostId());
        dto.setType(vote.getType().getVoteTypeID());
        return dto;
    }

    public Vote fromVoteDto(VoteDto voteDto, User user, Post post, VoteTypes voteType) {

        Vote vote = new Vote();
        vote.setCreatedBy(userRepository.getUserById(voteDto.getUserId()));
        vote.setPost(postRepository.getPostById(voteDto.getPostId()));
        vote.setType(voteTypesRepository.get(voteType.getVoteTypeID()));
        return vote;
    }
}
