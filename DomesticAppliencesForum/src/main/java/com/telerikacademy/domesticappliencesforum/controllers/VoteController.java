package com.telerikacademy.domesticappliencesforum.controllers;

import com.telerikacademy.domesticappliencesforum.exceptions.AuthorizationException;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.dtos.VoteDto;
import com.telerikacademy.domesticappliencesforum.repositories.PostRepository;
import com.telerikacademy.domesticappliencesforum.repositories.UserRepository;
import com.telerikacademy.domesticappliencesforum.services.VoteService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/votes")
public class VoteController {
    private final VoteService voteService;
    private final UserRepository userRepository;
    private PostRepository postRepository;
    private final AuthenticationHelper authenticationHelper;

    public VoteController(VoteService voteService, UserRepository userRepository,
                          PostRepository postRepository, AuthenticationHelper authenticationHelper) {
        this.voteService = voteService;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.authenticationHelper = authenticationHelper;
    }

    @PostMapping
    public void votePost(@RequestHeader HttpHeaders headers, @Valid @RequestBody VoteDto voteDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            voteService.votePost(voteDto,user);
        }catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
