package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.exceptions.UnauthorizedOperationException;
import com.telerikacademy.domesticappliencesforum.mappers.VoteMapper;
import com.telerikacademy.domesticappliencesforum.models.*;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.*;
import com.telerikacademy.domesticappliencesforum.services.interfaces.VoteCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class VoteCommentServiceImpl implements VoteCommentService {

    private final VoteCommentRepository voteCommentRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final VoteMapper voteMapper;
    private final VoteTypesRepository voteTypesRepository;

    @Autowired
    public VoteCommentServiceImpl(VoteCommentRepository voteCommentRepository, UserRepository userRepository,
                                  CommentRepository commentRepository, VoteMapper voteMapper,
                           VoteTypesRepository voteTypesRepository) {
        this.voteCommentRepository = voteCommentRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.voteMapper = voteMapper;
        this.voteTypesRepository = voteTypesRepository;
    }
    public void voteComment(VoteComment voteComment, User userReg) {
        voteComment.setCreatedBy(userReg);
        Comment comment = commentRepository.getCommentById(voteComment.getComment().getCommentId());
        VoteTypes type = voteTypesRepository.get(voteComment.getTypeId().getVoteTypeID());
        if (voteCommentRepository.existsByCreatedByAndCommentAndVoteType(userReg,comment,type)) {
            throw new IllegalArgumentException("User has already voted on this comment.");
        }else if(userReg.isBlocked()){
            throw new UnauthorizedOperationException("You`re blocked!!!");
        }
        voteCommentRepository.save(voteComment);
    }

    public List<VoteComment> getVoteCommentByCommentId(int commentId) {
        return voteCommentRepository.getVoteCommentByCommentId(commentId);
    }
}
