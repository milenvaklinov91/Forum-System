package com.telerikacademy.domesticappliencesforum.services;


import com.telerikacademy.domesticappliencesforum.exceptions.UnauthorizedOperationException;
import com.telerikacademy.domesticappliencesforum.mappers.VoteMapper;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.Vote;
import com.telerikacademy.domesticappliencesforum.models.VoteTypes;
import com.telerikacademy.domesticappliencesforum.models.dtos.VoteDto;
import com.telerikacademy.domesticappliencesforum.repositories.VoteRepositoryImpl;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.PostRepository;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.UserRepository;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.VoteRepository;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.VoteTypesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static com.telerikacademy.domesticappliencesforum.services.Helper.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VoteServiceImplsTest {

    @Mock
    private VoteRepository voteRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private VoteTypesRepository voteTypesRepository;
    @Mock
    private VoteMapper voteMapper;

    @InjectMocks
    private VoteServiceImpl voteService;

    @Test
    public void testVotePost_SuccessfulVote() {
        // Arrange
        Vote vote = Helper.createVote();
        User user = Helper.createMockUser();
        Post post = Helper.createMockPost();
        VoteTypes voteType = Helper.type();

        vote.setCreatedBy(user);
        vote.setType(voteType);
        vote.setPost(post);


        // Act
        voteService.votePost(vote, user);

        // Assert
        verify(voteRepository).save(vote);
    }


    @Test
    public void create_Should_Throw_When_UserIsBlocked() {
        // Arrange
        Vote vote = Helper.createVote();
        User user = Helper.createMockUser();
        Post post = Helper.createMockPost();
        VoteTypes voteType = Helper.type();

        vote.setCreatedBy(user);
        vote.setType(voteType);
        vote.setPost(post);
        user.setBlocked(true);

        // Act
        assertThrows(UnauthorizedOperationException.class, () -> voteService.votePost(vote, user));
    }

//    @Test
//    public void create_Should_Throw_When_User_Is_Voted(){
//        // Arrange
//        Vote vote = Helper.createVote();
//        User user = Helper.createMockUser();
//        Post post = Helper.createMockPost();
//        VoteTypes voteType = Helper.type();
//
//        vote.setCreatedBy(user);
//        vote.setType(voteType);
//        vote.setPost(post);
//
//        //Assert
//        when(voteRepository.existsByCreatedByAndPostAndVoteType(user,post,voteType)).thenReturn(true);
//
//        // Act
//        assertThrows(IllegalArgumentException.class, () -> voteService.votePost(vote,user));
//    }
}