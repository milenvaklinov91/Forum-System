package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.exceptions.EmailExitsException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityDuplicateException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.exceptions.UnauthorizedOperationException;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.domesticappliencesforum.services.Helper.createMockAdmin;
import static com.telerikacademy.domesticappliencesforum.services.Helper.createMockUser;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @Mock
    UserRepository userMockRepository;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    void getAllUsers_Should_Return_ListOfUsers() {
        List<User> returnUsers = new ArrayList<>();
        Mockito.when(userMockRepository.getAll()).thenReturn(returnUsers);

        List<User> users = userService.getAll();

        assertEquals(returnUsers, users);
    }

    @Test
    void countAllUsers_Should_Return_CountUsers() {
        Long returnUsers = 10L;
        Mockito.when(userMockRepository.countAllUsers()).thenReturn(returnUsers);

        Long users = userService.countAllUsers();

        assertEquals(returnUsers, users);
    }


    @Test
    void getById_Should_ReturnUser_When_MatchExist() {
        User mockUser = createMockUser();

        Mockito.when(userMockRepository.getUserById(Mockito.anyInt())).thenReturn(mockUser);
        User result = userService.getById(mockUser.getId());

        Assertions.assertEquals(mockUser, result);
    }

    @Test
    void getByUsername_Should_ReturnUser_When_MatchExist() {
        User mockUser = createMockUser();

        Mockito.when(userMockRepository.getByUsername(Mockito.anyString())).thenReturn(mockUser);
        User result = userService.getByUsername(mockUser.getUsername());

        Assertions.assertEquals(mockUser, result);
    }

    @Test
    void getByFirstName_Should_ReturnUser_When_MatchExist() {
        User mockUser = createMockUser();

        Mockito.when(userMockRepository.getByFirstName(Mockito.anyString())).thenReturn(mockUser);
        User result = userService.getByFirstName(mockUser.getFirstName());

        Assertions.assertEquals(mockUser, result);
    }

    @Test
    void getByEmail_Should_ReturnUser_When_MatchExist() {
        // Arrange
        User mockUser = createMockUser();
        //Act
        Mockito.when(userMockRepository.getByEmail(Mockito.anyString())).thenReturn(mockUser);
        User result = userService.getByEmail(mockUser.getUsername());
        // Assert
        Assertions.assertEquals(mockUser, result);
    }

    @Test
    void update_Should_UpdateUser_When_UserExistsAndAdmin() {
        User mockUser = createMockAdmin();
        User mockAdmin = createMockUser();

        when(userMockRepository.getUserById(mockUser.getId())).thenReturn(mockUser);

        userService.update(mockUser, mockAdmin);

        verify(userMockRepository, times(1)).update(mockUser);
        assertTrue(mockUser.isAdmin());
        assertFalse(mockUser.isBlocked());
    }

    @Test
    void update_Should_SetAdmin_When_UserIsAdmin() {
        User mockAdmin = createMockAdmin();
        User mockUser = createMockAdmin();

        when(userMockRepository.getUserById(mockUser.getId())).thenReturn(mockUser);

        userService.update(mockAdmin, mockUser);

        assertTrue(mockUser.isAdmin());
    }

    @Test
    void update_Should_SetBlocked_When_UserIsBlocked() {
        User mockUser = createMockUser();
        User mockBlockUser = createMockUser();
        mockBlockUser.setBlocked(true);

        when(userMockRepository.getUserById(mockBlockUser.getId())).thenReturn(mockBlockUser);

        userService.update(mockUser, mockBlockUser);

        assertTrue(mockBlockUser.isBlocked());
    }


    @Test
    void update_Should_ThrowConflictException_When_UsernameIsDiffer() {
        User mockUser = createMockUser();
        User mockUser1 = createMockUser();
        User userToUpdate = createMockUser();

        when(userMockRepository.getUserById(userToUpdate.getId())).thenReturn(mockUser1);

        mockUser1.setUsername("existingUsername");
        userToUpdate.setUsername("newUsername");

        ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                () -> userService.update(mockUser, userToUpdate));

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        assertEquals("You cannot update username!", exception.getReason());

        verify(userMockRepository, never()).update(any());
    }

    @Test
    void update_Should_ThrowResponseStatusException_When_UserDoesNotExist() {
        User mockAdmin = createMockAdmin();
        User mockUser = createMockUser();

        when(userMockRepository.getUserById(mockUser.getId())).thenThrow(EntityNotFoundException.class);

        assertThrows(ResponseStatusException.class, () -> userService.update(mockAdmin, mockUser));
    }

    @Test
    void getLikedPostsByUser_Should_ThrowException_When_LikedPostsDoesNotExist() {
        User mockUser = createMockUser();
        List<Post> likedPosts = new ArrayList<>();

        Mockito.when(userMockRepository.getLikedPostsByUser(mockUser.getId())).thenReturn(likedPosts);

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getLikedPostsByUser(mockUser.getId()));
    }

    @Test
    void getLikedPostsByUser_Should_ReturnListOfLikedPosts_WhenLikedPostsExist() {
        User mockUser = createMockUser();
        List<Post> expectedLikedPosts = new ArrayList<>();
        Post post = new Post();
        expectedLikedPosts.add(post);

        Mockito.when(userMockRepository.getLikedPostsByUser(mockUser.getId())).thenReturn(expectedLikedPosts);

        List<Post> actualLikedPosts = userService.getLikedPostsByUser(mockUser.getId());

        Assertions.assertEquals(expectedLikedPosts, actualLikedPosts);
    }

    @Test
    void getDislikedPostsByUser_Should_ThrowException_When_NoDisLikedPosts() {
        User mockUser = createMockUser();
        List<Post> dislikedPosts = new ArrayList<>();

        Mockito.when(userMockRepository.getDisLikedPostsByUser(mockUser.getId())).thenReturn(dislikedPosts);

        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getDisLikedPostsByUser(mockUser.getId()));
    }

    @Test
    void getDislikedPostsByUser_Should_ReturnDislikedPosts_WhenDislikedPostsExist() {
        User mockUser = createMockUser();
        List<Post> expectedDislikedPosts = new ArrayList<>();
        Post post = new Post();
        expectedDislikedPosts.add(post);

        Mockito.when(userMockRepository.getDisLikedPostsByUser(mockUser.getId())).thenReturn(expectedDislikedPosts);

        List<Post> actualDislikedPosts = userService.getDisLikedPostsByUser(mockUser.getId());

        Assertions.assertEquals(expectedDislikedPosts, actualDislikedPosts);
    }

    @Test
    void createUser_Should_CreateUser_When_UserCreated() {
        User mockUser = createMockUser();

        Mockito.when(userMockRepository.getByUsername(mockUser.getUsername())).thenThrow(EntityNotFoundException.class);
        Mockito.when(userMockRepository.getByEmail(mockUser.getEmail())).thenThrow(EntityNotFoundException.class);

        userService.create(mockUser);

        Mockito.verify(userMockRepository, times(1)).create(mockUser);
        assertEquals(LocalDateTime.now().getDayOfYear(), mockUser.getRegistrationDate().getDayOfYear());
    }

    @Test
    void createUser_Should_Throw_EntityDuplicateException_When_DuplicateUsernameExists() {
        User mockUser = createMockUser();
        Mockito.when(userMockRepository.getByUsername(mockUser.getUsername())).thenReturn(mockUser);

        assertThrows(EntityDuplicateException.class, () -> userService.create(mockUser));
        Mockito.verify(userMockRepository, Mockito.never()).create(mockUser);
    }

    @Test
    void createUser_Should_Throw_EmailExistsException_When_DuplicateEmailExists() {
        User mockUser = createMockUser();
        Mockito.when(userMockRepository.getByUsername(mockUser.getUsername())).thenThrow(EntityNotFoundException.class);
        Mockito.when(userMockRepository.getByEmail(mockUser.getEmail())).thenReturn(mockUser);

        assertThrows(EmailExitsException.class, () -> userService.create(mockUser));

        Mockito.verify(userMockRepository, Mockito.never()).create(mockUser);
    }

    @Test
    void getUserDetails_Should_ReturnUser_When_UserIsAdmin() {
        User mockAdmin = createMockAdmin();
        Mockito.when(userMockRepository.getUserById(mockAdmin.getId())).thenReturn(mockAdmin);

        User result = userService.getUserDetails(mockAdmin.getId(), mockAdmin);

        Mockito.verify(userMockRepository, times(1)).getUserById(mockAdmin.getId());
        Assertions.assertEquals(mockAdmin, result);
    }

    @Test
    void getUserDetails_Should_Return_UnauthorizedOperationException_When_UserIsNotAdmin() {
        User mockUser = createMockUser();

        Mockito.when(userMockRepository.getUserById(mockUser.getId())).thenReturn(mockUser);

        Assertions.assertThrows(UnauthorizedOperationException.class, () ->
                userService.getUserDetails(mockUser.getId(), mockUser));
        Mockito.verify(userMockRepository, times(1)).
                getUserById(mockUser.getId());
    }

    @Test
    void blockUser_Should_BlockUser_When_UserIsAdminAndUserIsNotBlocked() {
        User mockUser = createMockUser();
        User mockAdmin = createMockAdmin();

        when(userMockRepository.getUserById(mockUser.getId())).thenReturn(mockUser);
        doNothing().when(userMockRepository).update(any());

        userService.blockUser(mockUser.getId(), mockAdmin);

        assertTrue(mockUser.isBlocked());
        verify(userMockRepository, times(1)).update(mockUser);
    }

    @Test
    void blockUser_Should_ThrowException_When_UserIsNotAdmin() {
        User mockUser = createMockUser();
        User mockUserToBlock = createMockUser();
        mockUserToBlock.setBlocked(true);

        Mockito.when(userMockRepository.getUserById(mockUser.getId())).thenReturn(mockUser);

        Assertions.assertThrows(UnauthorizedOperationException.class, () -> userService.blockUser(mockUser.getId(), mockUserToBlock));

        Mockito.verify(userMockRepository, times(1)).getUserById(mockUser.getId());
        Mockito.verify(userMockRepository, Mockito.never()).update(mockUserToBlock);
    }

    @Test
    void unblockUser_Should_UnblockUser_When_UserIsAdminAndUnblockedUserIsBlocked() {
        User mockUser = createMockUser();
        mockUser.setBlocked(true);
        User mockAdmin = createMockAdmin();

        when(userMockRepository.getUserById(mockUser.getId())).thenReturn(mockUser);
        doNothing().when(userMockRepository).update(any());

        userService.unBlockUser(mockUser.getId(), mockAdmin);

        assertFalse(mockUser.isBlocked());
        verify(userMockRepository, times(1)).update(mockUser);
    }

    @Test
    void unblockUser_Should_ThrowException_When_UserIsNotAdmin() {
        User mockUser = createMockUser();
        User mockUserToBlock = createMockUser();

        Mockito.when(userMockRepository.getUserById(mockUser.getId())).thenReturn(mockUser);

        Assertions.assertThrows(UnauthorizedOperationException.class, () -> userService.unBlockUser(mockUser.getId(), mockUserToBlock));

        Mockito.verify(userMockRepository, times(1)).getUserById(mockUser.getId());
        Mockito.verify(userMockRepository, Mockito.never()).update(mockUserToBlock);
    }

    @Test
    void makeAdmin_Should_ThrowUnauthorizedOperationException_When_UserIsNotAdmin() {
        User mockUser1 = createMockUser();
        User mockUser = createMockUser();


        Mockito.when(userMockRepository.getUserById(mockUser1.getId())).thenReturn(mockUser1);
        Mockito.when(userMockRepository.getUserById(mockUser.getId())).thenReturn(mockUser);

        Assertions.assertThrows(UnauthorizedOperationException.class, () -> userService.makeAdmin(mockUser1.getId(), mockUser));

        Mockito.verify(userMockRepository, times(1)).getUserById(mockUser.getId());
        Mockito.verify(userMockRepository, times(1)).getUserById(mockUser1.getId());
        Mockito.verify(userMockRepository, Mockito.never()).update(any());
    }

    @Test
    void makeAdmin_Should_MakeUserAdmin_When_UserIsAdminAndUserIsNotAdmin() {
        User mockUser = createMockUser();
        User mockAdmin = createMockAdmin();

        when(userMockRepository.getUserById(mockUser.getId())).thenReturn(mockUser);
        doNothing().when(userMockRepository).update(any());

        userService.makeAdmin(mockUser.getId(), mockAdmin);

        assertTrue(mockUser.isAdmin());
        verify(userMockRepository, times(1)).update(mockUser);
    }

    @Test
    void unMakeAdmin_Should_ThrowUnauthorizedOperationException_When_UserIsNotAdmin() {
        User mockAdmin = createMockUser();

        User mockTargetUser = createMockUser();

        Mockito.when(userMockRepository.getUserById(mockAdmin.getId())).thenReturn(mockAdmin);
        Mockito.when(userMockRepository.getUserById(mockTargetUser.getId())).thenReturn(mockTargetUser);

        Assertions.assertThrows(UnauthorizedOperationException.class, () -> {
            userService.demoteAdmin(mockAdmin.getId(), mockTargetUser);
        });

        Mockito.verify(userMockRepository, times(1)).getUserById(mockAdmin.getId());
        Mockito.verify(userMockRepository, times(1)).getUserById(mockTargetUser.getId());
        Mockito.verify(userMockRepository, times(0)).update(any(User.class));
        Mockito.verifyNoMoreInteractions(userMockRepository);
    }

    @Test
    void unMakeAdmin_Should_UnMakeUserAdmin_When_UserIsAdminAndUserIsAdmin() {
        User mockAdmin = createMockAdmin();
        User mockAdmin1 = createMockAdmin();

        when(userMockRepository.getUserById(mockAdmin.getId())).thenReturn(mockAdmin);
        doNothing().when(userMockRepository).update(any());

        userService.demoteAdmin(mockAdmin.getId(), mockAdmin1);


        assertFalse(mockAdmin.isAdmin());
        verify(userMockRepository, times(1)).update(mockAdmin);
    }

}
