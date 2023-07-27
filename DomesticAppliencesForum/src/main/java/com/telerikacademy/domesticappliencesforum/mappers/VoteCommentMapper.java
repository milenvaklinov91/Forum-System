package com.telerikacademy.domesticappliencesforum.mappers;

import com.telerikacademy.domesticappliencesforum.models.*;
import com.telerikacademy.domesticappliencesforum.models.dtos.PostDto;
import com.telerikacademy.domesticappliencesforum.models.dtos.VoteCommentDto;
import com.telerikacademy.domesticappliencesforum.models.dtos.VoteDto;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.CommentRepository;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.PostRepository;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.UserRepository;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.VoteTypesRepository;
import com.telerikacademy.domesticappliencesforum.services.interfaces.CommentService;
import com.telerikacademy.domesticappliencesforum.services.interfaces.PostService;
import com.telerikacademy.domesticappliencesforum.services.interfaces.VoteCommentService;
import org.springframework.stereotype.Component;

@Component
public class VoteCommentMapper {

    private VoteTypesRepository voteTypesRepository;
    private CommentService commentService;
    private UserRepository userRepository;
    private CommentRepository commentRepository;

    public VoteCommentMapper(VoteTypesRepository voteTypesRepository, CommentService commentService, UserRepository userRepository,CommentRepository commentRepository) {
        this.voteTypesRepository = voteTypesRepository;
        this.commentService = commentService;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    public VoteComment fromVoteCommentDto(VoteCommentDto voteCommentDtoDto) {
        VoteComment voteComment = new VoteComment();
        voteComment.setComment(commentService.getCommentById(voteCommentDtoDto.getCommentId()));
        voteComment.setTypeId(voteTypesRepository.get(voteCommentDtoDto.getType()));
        return voteComment;
    }

}