package com.telerikacademy.domesticappliencesforum.services;

import com.sun.xml.bind.v2.TODO;
import com.telerikacademy.domesticappliencesforum.exceptions.EmailExitsException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityDuplicateException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.exceptions.UnauthorizedOperationException;
import com.telerikacademy.domesticappliencesforum.models.Post;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.domesticappliencesforum.services.Helper.createMockAdmin;
import static com.telerikacademy.domesticappliencesforum.services.Helper.createMockUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


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

        List<Post> actualLikedPosts = userService.getLikedPostsByUser(mockUser.getId()); //TODO

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
        //todo
        Mockito.verify(userMockRepository, Mockito.times(1)).create(mockUser);
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
        // Arrange
        User mockUser = createMockUser();
        Mockito.when(userMockRepository.getByUsername(mockUser.getUsername())).thenThrow(EntityNotFoundException.class);
        Mockito.when(userMockRepository.getByEmail(mockUser.getEmail())).thenReturn(mockUser);

        // Act & Assert
        assertThrows(EmailExitsException.class, () -> userService.create(mockUser));
        Mockito.verify(userMockRepository, Mockito.never()).create(mockUser);
    }

    @Test
    void getUserDetails_Should_ReturnUser_When_UserIsAdmin() {
        int adminId = 1;
        User mockAdmin = createMockAdmin();
        Mockito.when(userMockRepository.getUserById(adminId)).thenReturn(mockAdmin);

        User result = userService.getUserDetails(adminId, mockAdmin);

        Mockito.verify(userMockRepository, Mockito.times(1)).getUserById(adminId);
        Assertions.assertEquals(mockAdmin, result);
    }

    @Test
    void getUserDetails_Should_Return_UnauthorizedOperationException_When_UserIsNotAdmin() {
        int notAdminId = 2;
        User mockUser = createMockUser();
        mockUser.setAdmin(false);

        Mockito.when(userMockRepository.getUserById(notAdminId)).thenReturn(mockUser);


        Assertions.assertThrows(UnauthorizedOperationException.class, () -> userService.getUserDetails(notAdminId, mockUser));
        Mockito.verify(userMockRepository, Mockito.times(1)).getUserById(notAdminId);
    }

    @Test
    void blockUser_Should_BlockUser_When_UserIsAdminAndBlockedUserIsNotAdmin() {
        int adminId = 1;
        User mockAdmin = createMockUser();
        mockAdmin.setAdmin(true);

        int blockedUserId = 2;
        User mockUser = createMockUser();
        mockUser.setAdmin(false);
        //todo doesnt work TODO
        Mockito.when(userMockRepository.getUserById(adminId)).thenReturn(mockAdmin);
        Mockito.when(userMockRepository.getUserById(blockedUserId)).thenReturn(mockUser);

        Mockito.when(mockAdmin.isAdmin()).thenReturn(true);

        User result = userService.blockUser(adminId, mockUser);


        Mockito.verify(userMockRepository, Mockito.times(1)).getUserById(adminId);
        Mockito.verify(userMockRepository, Mockito.times(1)).getUserById(blockedUserId);
        Mockito.verify(userMockRepository, Mockito.times(1)).update(mockUser);

        Assertions.assertEquals(mockUser, result);
        Assertions.assertTrue(mockUser.isBlocked());
    }

    @Test
    void blockUser_Should_ThrowException_When_UserIsNotAdmin() {
        int notAdminId = 2;
        User mockUser = createMockUser();
        mockUser.setAdmin(false);

        User mockUserToBlock = createMockUser();
        mockUserToBlock.setAdmin(false);

        Mockito.when(userMockRepository.getUserById(notAdminId)).thenReturn(mockUser);

        Assertions.assertThrows(UnauthorizedOperationException.class, () -> userService.blockUser(notAdminId, mockUserToBlock));

        Mockito.verify(userMockRepository, Mockito.times(1)).getUserById(notAdminId);
        Mockito.verify(userMockRepository, Mockito.never()).update(mockUserToBlock);
    }

    @Test
    void unblockUser_Should_UnblockUser_When_UserIsAdminAndUnblockedUserIsNotAdmin() {
        int adminId = 1;
        User mockAdmin = createMockUser();
        mockAdmin.setAdmin(true);
        //todo doesnt work TODO
        int blockedUserId = 2;
        User mockUser = createMockUser();
        mockUser.setAdmin(false);
        mockUser.setBlocked(true);

        Mockito.when(userMockRepository.getUserById(adminId)).thenReturn(mockAdmin);
        Mockito.when(userMockRepository.getUserById(blockedUserId)).thenReturn(mockUser);

        Mockito.when(mockAdmin.isAdmin()).thenReturn(true);

        User result = userService.blockUser(adminId, mockUser);


        Mockito.verify(userMockRepository, Mockito.times(1)).getUserById(adminId);
        Mockito.verify(userMockRepository, Mockito.times(1)).getUserById(blockedUserId);
        Mockito.verify(userMockRepository, Mockito.times(1)).update(mockUser);

        Assertions.assertEquals(mockUser, result);
        Assertions.assertTrue(mockUser.isBlocked());
    }

    @Test
    void unblockUser_Should_ThrowException_When_UserIsNotAdmin() {
        int notAdminId = 2;
        User mockUser = createMockUser();
        mockUser.setAdmin(false);

        User mockUserToBlock = createMockUser();
        mockUserToBlock.setAdmin(false);

        Mockito.when(userMockRepository.getUserById(notAdminId)).thenReturn(mockUser);

        Assertions.assertThrows(UnauthorizedOperationException.class, () -> userService.unBlockUser(notAdminId, mockUserToBlock));

        Mockito.verify(userMockRepository, Mockito.times(1)).getUserById(notAdminId);
        Mockito.verify(userMockRepository, Mockito.never()).update(mockUserToBlock);
    }
    @Test           //TODO
    void makeAdmin_Should_ThrowUnauthorizedOperationException_When_UserIsNotAdmin() {
        int userId = 1;
        User mockAdmin = createMockUser();
        mockAdmin.setAdmin(false);

        int targetUserId = 2;
        User mockUser = createMockUser();
        mockUser.setAdmin(false);

        Mockito.when(userMockRepository.getUserById(userId)).thenReturn(mockAdmin);
        Mockito.when(userMockRepository.getUserById(targetUserId)).thenReturn(mockUser);

        Assertions.assertThrows(UnauthorizedOperationException.class, () -> userService.makeAdmin(targetUserId, mockAdmin));

        Mockito.verify(userMockRepository, Mockito.times(1)).getUserById(userId);
        Mockito.verify(userMockRepository, Mockito.times(1)).getUserById(targetUserId);
        Mockito.verify(userMockRepository, Mockito.never()).update(Mockito.any());
    }

    @Test   //TODO
    void makeAdmin_Should_MakeUserAdmin_When_UserIsAdminAndTargetUserIsNotAdmin() {
        int userId = 1;
        User mockAdmin = createMockUser();
        mockAdmin.setAdmin(true);

        int targetUserId = 2;
        User mockUser = createMockUser();
        mockUser.setAdmin(false);

        User spyUser = Mockito.spy(mockUser);

        Mockito.when(userMockRepository.getUserById(userId)).thenReturn(mockAdmin);
        Mockito.when(userMockRepository.getUserById(targetUserId)).thenReturn(spyUser);

        User result = userService.makeAdmin(targetUserId, mockAdmin);

        Mockito.verify(userMockRepository, Mockito.times(1)).getUserById(userId);
        Mockito.verify(userMockRepository, Mockito.times(1)).getUserById(targetUserId);
        Mockito.verify(userMockRepository, Mockito.times(1)).update(spyUser);

        Assertions.assertTrue(spyUser.isAdmin());
        Assertions.assertEquals(spyUser, result);
    }

    @Test       //TODO
    void unMakeAdmin_Should_ThrowUnauthorizedOperationException_When_UserIsNotAdmin() {
        int userId = 1;
        User mockAdmin = createMockUser();
        mockAdmin.setAdmin(false);

        int targetUserId = 2;
        User mockTargetUser = createMockUser();
        mockTargetUser.setAdmin(true);

        Mockito.when(userMockRepository.getUserById(userId)).thenReturn(mockAdmin);
        Mockito.when(userMockRepository.getUserById(targetUserId)).thenReturn(mockTargetUser);

        Assertions.assertThrows(UnauthorizedOperationException.class, () -> {
            userService.unMakeAdmin(userId, mockTargetUser);
        });

        Mockito.verify(userMockRepository, Mockito.times(1)).getUserById(userId);
        Mockito.verify(userMockRepository, Mockito.times(1)).getUserById(targetUserId);
        Mockito.verify(userMockRepository, Mockito.times(0)).update(Mockito.any(User.class));
        Mockito.verifyNoMoreInteractions(userMockRepository);
    }
    @Test           //TODO
    void unMakeAdmin_Should_UnMakeUserAdmin_When_UserIsAdminAndTargetUserIsAdmin() {
        int userId = 1;
        User mockAdmin = createMockUser();
        mockAdmin.setAdmin(true);

        int targetUserId = 2;
        User mockTargetUser = createMockUser();
        mockTargetUser.setAdmin(true);

        Mockito.when(userMockRepository.getUserById(userId)).thenReturn(mockAdmin);
        Mockito.when(userMockRepository.getUserById(targetUserId)).thenReturn(mockTargetUser);

        User result = userService.unMakeAdmin(userId, mockTargetUser);

        Mockito.verify(userMockRepository, Mockito.times(1)).getUserById(userId);
        Mockito.verify(userMockRepository, Mockito.times(1)).getUserById(targetUserId);
        Mockito.verify(userMockRepository, Mockito.times(1)).update(mockTargetUser);
        Mockito.verifyNoMoreInteractions(userMockRepository);

        Assertions.assertFalse(result.isAdmin());
    }

}
