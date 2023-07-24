package com.telerikacademy.domesticappliencesforum.controllers.rest;

import com.telerikacademy.domesticappliencesforum.controllers.AuthenticationHelper;
import com.telerikacademy.domesticappliencesforum.exceptions.AuthorizationException;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.dtos.VoteDto;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.PostRepository;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.UserRepository;
import com.telerikacademy.domesticappliencesforum.services.interfaces.VoteService;
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

//    @GetMapping("/{id}/likes")
//    public int getLikeForPost(@Valid @PathVariable int id) {
//
//        return voteService.getVoteCountForPost(id);
//    }
//
//    @GetMapping("/{id}/dislikes")
//    public int getDislikeForPost(@Valid @PathVariable int id) {
//        return voteService.getDislikeForPost(id);
//    }

    @PostMapping
    public void votePost(@RequestHeader HttpHeaders headers, @Valid @RequestBody VoteDto voteDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            voteService.votePost(voteDto, user);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }



}