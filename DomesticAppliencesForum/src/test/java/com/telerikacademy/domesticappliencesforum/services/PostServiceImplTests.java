package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.exceptions.UnauthorizedOperationException;
import com.telerikacademy.domesticappliencesforum.models.Comment;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.models.filterOptions.PostFilterOptions;
import com.telerikacademy.domesticappliencesforum.repositories.PostRepositoryImpl;
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

import static com.telerikacademy.domesticappliencesforum.services.Helper.createMockPost;
import static com.telerikacademy.domesticappliencesforum.services.Helper.createMockUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTests {
    @Mock
    PostRepositoryImpl postRepository;
    @InjectMocks
    PostServiceImpl postService;

    @Test
    public void testGetAllPosts() {
        // Arrange
        List<Post> expectedPosts = new ArrayList<>();
        PostFilterOptions filterOptions=Helper.createMockFilterOptions();
        when(postRepository.getAllPosts(filterOptions))
                .thenReturn(expectedPosts);

        // Act
        List<Post> result = postService.getAllPosts(filterOptions);

        // Assert
        assertEquals(expectedPosts, result);
    }
    @Test
    public void testCountAllPosts(){
        //Arrange
        Long expectedCount = 1L;
        when(postRepository.countAllPosts()).thenReturn(expectedCount);

        //Act
        long actualCount = postService.countAllPosts();

        // Assert
        assertEquals(expectedCount, actualCount);
    }
    @Test
    public void testGetById() {
        //Arrange
        Post mockPost = createMockPost();

        Mockito.when(postRepository.getPostById(Mockito.anyInt()))
                .thenReturn(mockPost);

        // Act
        Post result = postService.getById(mockPost.getPostId());

        // Assert
        Assertions.assertEquals(mockPost, result);
    }

    @Test
    public void testCreate() {
        // Arrange
        User mockUser = Helper.createMockUser();
        Post mockPost = createMockPost();
        mockPost.setCreatedBy(mockUser);

        // Act
        postService.create(mockPost, mockUser);

        // Assert
        verify(postRepository).create(mockPost);
    }

    @Test
    public void create_Should_Throw_When_UserIsBlocked() {
        // Arrange
        Post mockPost = createMockPost();
        User mockUser = createMockUser();
        mockUser.setBlocked(true);

        // Act
        assertThrows(UnauthorizedOperationException.class, () -> postService.create(mockPost, mockUser));
    }

    @Test
    public void testModify(){
        //Assert
        Post mockPost = createMockPost();
        User mockUser = createMockUser();

        //Act
        postService.modify(mockPost, mockUser);

        // Assert
        verify(postRepository).modify(mockPost);
    }

    @Test
    public void modify_Should_Throw_When_UserIsBlocked() {
        // Arrange
        User blockedMockUser = createMockUser();
        blockedMockUser.setBlocked(true);
        Post mockPost = createMockPost();
        mockPost.setCreatedBy(blockedMockUser);

        // Act & Assert
        assertThrows(UnauthorizedOperationException.class, () -> postService.modify(mockPost, blockedMockUser));

        verify(postRepository, never()).modify(any(Post.class));
    }

    @Test
    public void testModify_Should_Throw_When_UserIsNotCreator() {
        // Arrange
        User createdByMockUser = createMockUser();
        User anotherUser = createMockUser();
        Post mockPost = createMockPost();
        mockPost.setCreatedBy(createdByMockUser);

        doThrow(UnauthorizedOperationException.class).when(postRepository).modify(mockPost);

        // Act & Assert
        assertThrows(UnauthorizedOperationException.class, () -> postService.modify(mockPost, anotherUser));
        verify(postRepository).modify(mockPost);
    }

    @Test
    public void testModify_Should_Throw_When_UserIsNotAdmin() {
        // Arrange
        User createdByMockUser = createMockUser();
        User anotherUser = createMockUser();
        Post mockPost = createMockPost();
        mockPost.setCreatedBy(createdByMockUser);
        anotherUser.setAdmin(false);

        doThrow(UnauthorizedOperationException.class).when(postRepository).modify(mockPost);

        // Act & Assert
        assertThrows(UnauthorizedOperationException.class, () -> postService.modify(mockPost, anotherUser));
        verify(postRepository).modify(mockPost);
    }

    @Test
    public void testDelete_Should_CallRepository_When_UserIsAdmin() {
        // Arrange
        int postId = 1;
        User adminUser = createMockUser();
        adminUser.setAdmin(true);


        Post mockPost = createMockPost();
        when(postRepository.getPostById(postId)).thenReturn(mockPost);

        // Act & Assert
        assertDoesNotThrow(() -> postService.delete(postId, adminUser));
        verify(postRepository).delete(postId);
    }

    @Test
    public void testDelete_Should_CallRepository_When_UserIsCreator() {
        // Arrange
        int postId = 1;
        User createdByMockUser = createMockUser();
        Post mockPost = createMockPost();
        mockPost.setCreatedBy(createdByMockUser);

        when(postRepository.getPostById(postId)).thenReturn(mockPost);

        // Act & Assert
        assertDoesNotThrow(() -> postService.delete(postId, createdByMockUser));
        verify(postRepository).delete(postId);
    }

   @Test
    public void testDelete_Should_Throw_When_UserIsNotCreatorAndNotAdmin() {
        // Arrange
        int postId = 1;
        User createdByMockUser = createMockUser();
        User anotherUser = createMockUser();
        Post mockPost = createMockPost();
        mockPost.setCreatedBy(createdByMockUser);

        when(postRepository.getPostById(postId)).thenReturn(mockPost);
        doThrow(UnauthorizedOperationException.class).when(postRepository).delete(postId);

        // Act & Assert
        assertThrows(UnauthorizedOperationException.class, () -> postService.delete(postId, anotherUser));
        verify(postRepository).delete(postId);
    }

    @Test
    public void testDelete_Should_Throw_When_UserIsBlocked() {
        // Arrange
        int postId = 1;
        User blockedMockUser = createMockUser();
        blockedMockUser.setBlocked(true);
        Post mockPost = createMockPost();
        mockPost.setCreatedBy(blockedMockUser);

        when(postRepository.getPostById(postId)).thenReturn(mockPost);

        // Act & Assert
        assertThrows(UnauthorizedOperationException.class, () -> postService.delete(postId, blockedMockUser));
        verify(postRepository, never()).delete(postId);
    }

    @Test
    public void testGetAllComments_Should_ReturnComments_When_PostHasComments() {
        // Arrange
        int postId = 1;
        Post mockPost = createMockPost();
        List<Comment> comments = new ArrayList<>();
        Comment comment1 = Helper.createMockComment();
        Comment comment2 = Helper.createMockComment();
        comments.add(comment1);
        comments.add(comment2);
        mockPost.setComments(comments);

        when(postRepository.getPostById(postId)).thenReturn(mockPost);

        // Act
        List<Comment> result = postService.getAllComments(postId);

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(comments.size(), result.size());
        assertTrue(result.contains(comment1));
        assertTrue(result.contains(comment2));
    }

    @Test
    public void testGetAllComments_Should_ThrowEntityNotFoundException_When_PostHasNoComments() {
        // Arrange
        int postId = 1;
        Post mockPost = createMockPost();
        mockPost.setComments(new ArrayList<>());

        when(postRepository.getPostById(postId)).thenReturn(mockPost);

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> postService.getAllComments(postId));
    }

    @Test
    public void testGetPostLikes_Should_ReturnPositiveLikes() {
        // Arrange
        int postId = 1;
        int expectedLikes = 1;

        when(postRepository.getPostLikes(postId)).thenReturn(expectedLikes);

        // Act
        int actualLikes = postService.getPostLikes(postId);

        // Assert
        assertEquals(expectedLikes, actualLikes);
    }

    @Test
    public void testGetPostLikes_Should_ReturnZeroLikes() {
        // Arrange
        int postId = 1;
        int expectedLikes = 0;

        when(postRepository.getPostLikes(postId)).thenReturn(expectedLikes);
        // Act
        int actualLikes = postService.getPostLikes(postId);
        // Assert
        assertEquals(expectedLikes, actualLikes);
    }
    @Test
    public void testGetPostDisLikes_Should_ReturnPositiveLikes() {
        int postId = 1;
        int expectedDisLikes = 1;

        when(postRepository.getPostDisLikes(postId)).thenReturn(expectedDisLikes);
        // Act
        int result = postService.getPostDisLikes(postId);

        // Assert
        assertEquals(expectedDisLikes, result);
    }

    @Test
    public void testGetPostDisLikes_Should_ReturnZeroLikes() {
        // Arrange
        int postId = 1;
        int expectedDisLikes = 0;

        when(postRepository.getPostLikes(postId)).thenReturn(expectedDisLikes);

        // Act
        int actualDisLikes = postService.getPostLikes(postId);

        // Assert
        assertEquals(expectedDisLikes, actualDisLikes);
    }

}
