package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityDuplicateException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
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
    void getAllUsers_Should_Return_ListOfUsers(){
        // Arrange
        List<User> returnUsers = new ArrayList<>();
        Mockito.when(userMockRepository.getAll()).thenReturn(returnUsers);
        //Act
        List<User> users = userService.getAll();
        // Assert
        assertEquals(returnUsers, users);
    }

    @Test
    void countAllUsers_Should_Return_CountUsers(){
        // Arrange
        Long returnUsers = 10L;
        Mockito.when(userMockRepository.countAllUsers()).thenReturn(returnUsers);
        //Act
        Long users = userService.countAllUsers();
        // Assert
        assertEquals(returnUsers, users);
    }


    @Test
    void getById_Should_ReturnUser_When_MatchExist(){
        // Arrange
        User mockUser = createMockUser();
        //Act
        Mockito.when(userMockRepository.getUserById(Mockito.anyInt())).thenReturn(mockUser);
        User result = userService.getById(mockUser.getId());
        // Assert
        Assertions.assertEquals(mockUser , result);
    }

    @Test
    void getByUsername_Should_ReturnUser_When_MatchExist(){
        // Arrange
        User mockUser = createMockUser();
        //Act
        Mockito.when(userMockRepository.getByUsername(Mockito.anyString())).thenReturn(mockUser);
        User result = userService.getByUsername(mockUser.getUsername());
        // Assert
        Assertions.assertEquals(mockUser , result);
    }

    @Test
    void getByFirstName_Should_ReturnUser_When_MatchExist(){
        // Arrange
        User mockUser = createMockUser();
        //Act
        Mockito.when(userMockRepository.getByFirstName(Mockito.anyString())).thenReturn(mockUser);
        User result = userService.getByFirstName(mockUser.getFirstName());
        // Assert
        Assertions.assertEquals(mockUser , result);
    }

    @Test
    void getByEmail_Should_ReturnUser_When_MatchExist(){
        // Arrange
        User mockUser = createMockUser();
        //Act
        Mockito.when(userMockRepository.getByEmail(Mockito.anyString())).thenReturn(mockUser);
        User result = userService.getByEmail(mockUser.getUsername());
        // Assert
        Assertions.assertEquals(mockUser , result);
    }

    @Test
    void getLikedPostsByUser_Should_ThrowException_When_LikedPostsDoesnotExist() {
        // Arrange
        User mockUser = createMockUser();
        List<Post> likedPosts = new ArrayList<>();

        //Act
        Mockito.when(userMockRepository.getLikedPostsByUser(mockUser.getId())).thenReturn(likedPosts);

        //Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getLikedPostsByUser(mockUser.getId()));
    }

    @Test
    void getLikedPostsByUser_Should_ReturnListOfLikedPosts_WhenLikedPostsExist() {
        //Arrange
        User mockUser = createMockUser();
        List<Post> expectedLikedPosts = new ArrayList<>();
        Post post = new Post();
        expectedLikedPosts.add(post);

        Mockito.when(userMockRepository.getLikedPostsByUser(mockUser.getId())).thenReturn(expectedLikedPosts);

        //Act
        List<Post> actualLikedPosts = userService.getLikedPostsByUser(mockUser.getId());

        //Assert
        Assertions.assertEquals(expectedLikedPosts, actualLikedPosts);
    }

    @Test
    void getDislikedPostsByUser_Should_ThrowException_When_NoDisLikedPosts() {
        // Arrange
        User mockUser = createMockUser();
        List<Post> dislikedPosts = new ArrayList<>();

        //Act
        Mockito.when(userMockRepository.getDisLikedPostsByUser(mockUser.getId())).thenReturn(dislikedPosts);

        //Assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> userService.getDisLikedPostsByUser(mockUser.getId()));
    }

    @Test
    void getDislikedPostsByUser_Should_ReturnDislikedPosts_WhenDislikedPostsExist() {
        //Arrange
        User mockUser = createMockUser();
        List<Post> expectedDislikedPosts = new ArrayList<>();
        Post post = new Post();
        expectedDislikedPosts.add(post);

        Mockito.when(userMockRepository.getDisLikedPostsByUser(mockUser.getId())).thenReturn(expectedDislikedPosts);

        //Act
        List<Post> actualDislikedPosts = userService.getDisLikedPostsByUser(mockUser.getId());

        //Assert
        Assertions.assertEquals(expectedDislikedPosts, actualDislikedPosts);
    }
    @Test
    void createUser_Should_CreateUser_When_UserCreated(){
        User mockUser = createMockUser();

        Mockito.when(userMockRepository.getUserById(Mockito.anyInt())).thenReturn(mockUser);
        userService.create(mockUser);


        Assertions.assertEquals(mockUser,new User());
    }

    @Test
    void create_Should_SetRegistrationDate_And_CallRepositoryCreate_When_NoDuplicates() {
        // Arrange
        User mockUser = createMockUser();
        Mockito.when(userMockRepository.getByUsername(mockUser.getUsername())).thenThrow(EntityNotFoundException.class);
        Mockito.when(userMockRepository.getByEmail(mockUser.getEmail())).thenThrow(EntityNotFoundException.class);

        // Act
        userService.create(mockUser);

        // Assert
        assertEquals(LocalDateTime.now().getDayOfYear(), mockUser.getRegistrationDate().getDayOfYear());
        Mockito.verify(userMockRepository, Mockito.times(1)).create(mockUser);
    }

    @Test
    void create_Should_Throw_EntityDuplicateException_When_DuplicateUsernameExists() {
        // Arrange
        User mockUser = createMockUser();
        Mockito.when(userMockRepository.getByUsername(mockUser.getUsername())).thenReturn(mockUser);

        // Act & Assert
        assertThrows(EntityDuplicateException.class, () -> userService.create(mockUser));
        Mockito.verify(userMockRepository, Mockito.never()).create(mockUser);
    }

//    @Test
//    void create_Should_Throw_EmailExistsException_When_DuplicateEmailExists() {
//        // Arrange
//        User mockUser = createMockUser();
//        Mockito.when(userMockRepository.getByUsername(mockUser.getUsername())).thenThrow(EntityNotFoundException.class);
//        Mockito.when(userMockRepository.getByEmail(mockUser.getEmail())).thenReturn(mockUser);
//
//        // Act & Assert
//        assertThrows(EmailExistsException.class, () -> userService.create(mockUser));
//        Mockito.verify(userMockRepository, Mockito.never()).create(mockUser);
//    }



}
