package com.telerikacademy.domesticappliencesforum.services;

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
import java.util.List;

import static com.telerikacademy.domesticappliencesforum.services.Helper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
}