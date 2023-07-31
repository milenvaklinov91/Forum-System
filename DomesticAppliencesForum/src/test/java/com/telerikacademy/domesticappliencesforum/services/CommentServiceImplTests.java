package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.exceptions.UnauthorizedOperationException;
import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.FilterOptionsComment;
import com.telerikacademy.domesticappliencesforum.repositories.CommentRepositoryImpl;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.CommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.telerikacademy.domesticappliencesforum.services.Helper.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTests {

    @Mock
    CommentRepositoryImpl commentRepository;

    @InjectMocks
    CommentServiceImpl commentService;

    @Test
    public void testGetAllComments() {
        // Arrange
        List<Comment> expectedComments = new ArrayList<>();
        FilterOptionsComment filterOptionsComment = Helper.createMockFilterOptionsComment();
        when(commentRepository.getAllComments(filterOptionsComment))
                .thenReturn(expectedComments);

        // Act
        List<Comment> result = commentService.getAllComments(filterOptionsComment);

        // Assert
        assertEquals(expectedComments, result);
    }

    @Test
    public void testCountAllComments(){
        //Arrange
        Long expectedCount = 1L;
        when(commentRepository.countAllComments()).thenReturn(expectedCount);

        //Act
        long actualCount = commentService.countAllComments();

        // Assert
        assertEquals(expectedCount, actualCount);
    }

    @Test
    public void testGetCommentById() {
        //Arrange
        Comment mockComment = createMockCommentComment();

        Mockito.when(commentRepository.getCommentById(Mockito.anyInt()))
                .thenReturn(mockComment);

        // Act
        Comment result = commentService.getCommentById(mockComment.getCommentId());

        // Assert
        Assertions.assertEquals(mockComment,result);
    }

    @Test
    public void testCreateComment() {
        // Arrange
        User mockUser = Helper.createMockUser();
        Comment mockComment = createMockCommentComment();
        mockComment.setCreatedByUser(mockUser);

        // Act
        commentService.create(mockComment, mockUser);

        // Assert
        verify(commentRepository).create(mockComment);
    }
    @Test
    public void createComment_Should_Throw_When_UserIsBlocked() {
        // Arrange
        Comment mockComment = createMockCommentComment();
        User mockUser = createMockUser();
        mockUser.setBlocked(true);

        // Act
        assertThrows(UnauthorizedOperationException.class, () -> commentService.create(mockComment, mockUser));
    }
    @Test
    public void testModify(){
        //Assert
        Comment mockComment = createMockCommentComment();
        User mockUser = createMockUser();

        //Act
        commentService.modify(mockComment, mockUser);

        // Assert
        verify(commentRepository).modify(mockComment);
    }
    @Test
    public void modifyComment_Should_Throw_When_UserIsBlocked() {
        // Arrange
        User blockedMockUser = createMockUser();
        blockedMockUser.setBlocked(true);
        Comment mockComment = createMockCommentComment();
        mockComment.setCreatedByUser(blockedMockUser);

        // Act & Assert
        assertThrows(UnauthorizedOperationException.class, () -> commentService.modify(mockComment, blockedMockUser));

        verify(commentRepository, never()).modify(any(Comment.class));
    }
    @Test
    public void testModifyComment_Should_Throw_When_UserIsNotCreator() {
        // Arrange
        User createdByMockUser = createMockUser();
        User anotherUser = createMockUser();
        Comment mockComment = createMockCommentComment();
        mockComment.setCreatedByUser(createdByMockUser);

        doThrow(UnauthorizedOperationException.class).when(commentRepository).modify(mockComment);

        // Act & Assert
        assertThrows(UnauthorizedOperationException.class, () -> commentService.modify(mockComment, anotherUser));
        verify(commentRepository).modify(mockComment);
    }
    @Test
    public void testModifyComment_Should_Throw_When_UserIsNotAdmin() {
        // Arrange
        User createdByMockUser = createMockUser();
        User anotherUser = createMockUser();
        Comment mockComment = createMockCommentComment();
        mockComment.setCreatedByUser(createdByMockUser);
        anotherUser.setAdmin(false);

        doThrow(UnauthorizedOperationException.class).when(commentRepository).modify(mockComment);

        // Act & Assert
        assertThrows(UnauthorizedOperationException.class, () -> commentService.modify(mockComment, anotherUser));
        verify(commentRepository).modify(mockComment);
    }
    @Test
    public void testDelete_Should_CallRepository_When_UserIsAdmin() {
        // Arrange
        int commentId = 1;
        User adminUser = createMockUser();
        adminUser.setAdmin(true);


        Comment mockComment = createMockCommentComment();
        when(commentRepository.getCommentById(commentId)).thenReturn(mockComment);

        // Act & Assert
        assertDoesNotThrow(() -> commentService.delete(commentId, adminUser));
        verify(commentRepository).delete(commentId);
    }

    @Test
    public void testDelete_Should_CallRepository_When_UserIsCreator() {
        // Arrange
        int commentId = 1;
        User createdByMockUser = createMockUser();
        Comment mockComment = createMockCommentComment();
        mockComment.setCreatedByUser(createdByMockUser);

        when(commentRepository.getCommentById(commentId)).thenReturn(mockComment);

        // Act & Assert
        assertDoesNotThrow(() -> commentService.delete(commentId, createdByMockUser));
        verify(commentRepository).delete(commentId);
    }

    @Test
    public void testDelete_Should_Throw_When_UserIsNotCreatorAndNotAdmin() {
        // Arrange
        int commentId = 1;
        User createdByMockUser = createMockUser();
        User anotherUser = createMockUser();
        Comment mockComment = createMockCommentComment();
        mockComment.setCreatedByUser(createdByMockUser);

        when(commentRepository.getCommentById(commentId)).thenReturn(mockComment);
        doThrow(UnauthorizedOperationException.class).when(commentRepository).delete(commentId);

        // Act & Assert
        assertThrows(UnauthorizedOperationException.class, () -> commentService.delete(commentId, anotherUser));
        verify(commentRepository).delete(commentId);
    }

    @Test
    public void testDelete_Should_Throw_When_UserIsBlocked() {
        // Arrange
        int commentId = 1;
        User blockedMockUser = createMockUser();
        blockedMockUser.setBlocked(true);
        Comment mockComment = createMockCommentComment();
        mockComment.setCreatedByUser(blockedMockUser);

        when(commentRepository.getCommentById(commentId)).thenReturn(mockComment);

        // Act & Assert
        assertThrows(UnauthorizedOperationException.class, () -> commentService.delete(commentId, blockedMockUser));
        verify(commentRepository, never()).delete(commentId);
    }

    @Test
    public void testGetCommentLikes_Should_ReturnPositiveLikes() {
        // Arrange
        int commentId = 1;
        int expectedLikes = 1;

        when(commentRepository.getCommentLikes(commentId)).thenReturn(expectedLikes);

        // Act
        int actualLikes = commentService.getCommentLikes(commentId);

        // Assert
        assertEquals(expectedLikes, actualLikes);
    }

    @Test
    public void testGetPostLikes_Should_ReturnZeroLikes() {
        // Arrange
        int commentId = 1;
        int expectedLikes = 0;

        when(commentRepository.getCommentLikes(commentId)).thenReturn(expectedLikes);
        // Act
        int actualLikes = commentService.getCommentLikes(commentId);
        // Assert
        assertEquals(expectedLikes, actualLikes);
    }
    @Test
    public void testGetPostDisLikes_Should_ReturnPositiveLikes() {
        int commentId = 1;
        int expectedDisLikes = 1;

        when(commentRepository.getCommentDisLikes(commentId)).thenReturn(expectedDisLikes);
        // Act
        int result = commentService.getCommentDisLikes(commentId);

        // Assert
        assertEquals(expectedDisLikes, result);
    }

    @Test
    public void testGetPostDisLikes_Should_ReturnZeroLikes() {
        // Arrange
        int commentId = 1;
        int expectedDisLikes = 0;

        when(commentRepository.getCommentLikes(commentId)).thenReturn(expectedDisLikes);

        // Act
        int actualDisLikes = commentService.getCommentLikes(commentId);

        // Assert
        assertEquals(expectedDisLikes, actualDisLikes);
    }

}