package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.exceptions.UnauthorizedOperationException;

import com.telerikacademy.domesticappliencesforum.models.*;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class VoteCommentServiceImplTests {

    @Mock
    private VoteCommentRepository voteCommentRepository;

    @InjectMocks
    private VoteCommentServiceImpl voteCommentService;
//todo both tests don't work
    @Test
    public void testVoteComment_SuccessfulVote() {
        // Arrange
        VoteComment voteComment = Helper.createVoteComment();
        User user = Helper.createMockUser();
        Comment comment = Helper.createMockCommentComment();
        VoteTypes voteType = Helper.type();

        voteComment.setCreatedBy(user);
        voteComment.setTypeId(voteType);
        voteComment.setComment(comment);


        // Act
        voteCommentService.voteComment(voteComment, user);

        // Assert
        verify(voteCommentRepository).save(voteComment);
    }


    @Test
    public void voteComment_Should_Throw_When_UserIsBlocked() {
        // Arrange
        VoteComment voteComment = Helper.createVoteComment();
        User user = Helper.createMockUser();
        Comment comment = Helper.createMockCommentComment();
        VoteTypes voteType = Helper.type();

        voteComment.setCreatedBy(user);
        voteComment.setTypeId(voteType);
        voteComment.setComment(comment);
        user.setBlocked(true);

        // Act
        assertThrows(UnauthorizedOperationException.class, () -> voteCommentService.voteComment(voteComment, user));
    }
}
