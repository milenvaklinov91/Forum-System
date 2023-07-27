package com.telerikacademy.domesticappliencesforum.controllers.rest;

import com.telerikacademy.domesticappliencesforum.controllers.AuthenticationHelper;
import com.telerikacademy.domesticappliencesforum.exceptions.AuthorizationException;
import com.telerikacademy.domesticappliencesforum.mappers.VoteCommentMapper;
import com.telerikacademy.domesticappliencesforum.mappers.VoteMapper;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.Vote;
import com.telerikacademy.domesticappliencesforum.models.VoteComment;
import com.telerikacademy.domesticappliencesforum.models.dtos.VoteCommentDto;
import com.telerikacademy.domesticappliencesforum.models.dtos.VoteDto;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.CommentRepository;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.PostRepository;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.UserRepository;
import com.telerikacademy.domesticappliencesforum.services.interfaces.VoteCommentService;
import com.telerikacademy.domesticappliencesforum.services.interfaces.VoteService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/voteComments")
public class VoteCommentController {
    private final VoteCommentService voteCommentService;
    private final UserRepository userRepository;
    private CommentRepository commentRepository;
    private final AuthenticationHelper authenticationHelper;
    private VoteCommentMapper voteCommentMapper;

    public VoteCommentController(VoteCommentService voteCommentService, UserRepository userRepository,
                          CommentRepository commentRepository, AuthenticationHelper authenticationHelper, VoteCommentMapper voteCommentMapper) {
        this.voteCommentService = voteCommentService;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.authenticationHelper = authenticationHelper;
        this.voteCommentMapper = voteCommentMapper;
    }

    @GetMapping("/{commentId}")
    public List<VoteComment> getVotesByCommentId(@PathVariable int commentId){
        return voteCommentService.getVoteCommentByCommentId(commentId);
    }

    @PostMapping
    public void voteComment(@RequestHeader HttpHeaders headers, @Valid @RequestBody VoteCommentDto voteCommentDto) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            VoteComment voteComment = voteCommentMapper.fromVoteCommentDto(voteCommentDto);
            voteCommentService.voteComment(voteComment, user);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }



}
