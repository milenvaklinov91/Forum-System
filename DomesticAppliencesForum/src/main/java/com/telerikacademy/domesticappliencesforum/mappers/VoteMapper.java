package com.telerikacademy.domesticappliencesforum.mappers;

import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.Vote;
import com.telerikacademy.domesticappliencesforum.models.VoteTypes;
import com.telerikacademy.domesticappliencesforum.models.dtos.PostDto;
import com.telerikacademy.domesticappliencesforum.models.dtos.VoteDto;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.PostRepository;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.UserRepository;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.VoteTypesRepository;
import com.telerikacademy.domesticappliencesforum.services.interfaces.PostService;
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

    public Vote fromVoteDto(VoteDto voteDto) {
        Vote vote = new Vote();
        vote.setPost(postService.getById(voteDto.getPostId()));
        vote.setType(voteTypesRepository.get(voteDto.getType()));
        return vote;
    }

}