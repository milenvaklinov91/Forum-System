package com.telerikacademy.domesticappliencesforum.services;

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

import java.util.ArrayList;
import java.util.List;

import static com.telerikacademy.domesticappliencesforum.services.Helper.createMockUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTests {

    @Mock
    UserRepository mockRepository;
    @InjectMocks
    UserServiceImpl userService;
    /*@Test
    void get_Should_CallRepository(){
        FilterOptions mockFilterOptions = createMockFilterOptions();

        userService.getAll(mockFilterOptions);

        Mockito.verify(mockRepository, Mockito.times(1)).getAll(mockFilterOptions);
    }*/
    @Test
    void getAllUsers_Should_Return_ListOfUsers(){
        // Arrange
        List<User> returnUsers = new ArrayList<>();
        Mockito.when(mockRepository.getAll()).thenReturn(returnUsers);
        //Act
        List<User> users = userService.getAll();
        // Assert
        assertEquals(returnUsers, users);
    }

    @Test
    void countAllUsers_Should_Return_CountUsers(){
        // Arrange
        Long returnUsers = 10L;
        Mockito.when(mockRepository.countAllUsers()).thenReturn(returnUsers);
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
        Mockito.when(mockRepository.getUserById(Mockito.anyInt())).thenReturn(mockUser);
        User result = userService.getById(mockUser.getId());
        // Assert
        Assertions.assertEquals(mockUser , result);
    }

    @Test
    void getByUsername_Should_ReturnUser_When_MatchExist(){
        // Arrange
        User mockUser = createMockUser();
        //Act
        Mockito.when(mockRepository.getByUsername(Mockito.anyString())).thenReturn(mockUser);
        User result = userService.getByUsername(mockUser.getUsername());
        // Assert
        Assertions.assertEquals(mockUser , result);
    }

    @Test
    void getByFirstName_Should_ReturnUser_When_MatchExist(){
        // Arrange
        User mockUser = createMockUser();
        //Act
        Mockito.when(mockRepository.getByFirstName(Mockito.anyString())).thenReturn(mockUser);
        User result = userService.getByUsername(mockUser.getFirstName());
        // Assert
        Assertions.assertEquals(mockUser , result);
    }

    @Test
    void getByEmail_Should_ReturnUser_When_MatchExist(){
        // Arrange
        User mockUser = createMockUser();
        //Act
        Mockito.when(mockRepository.getByEmail(Mockito.anyString())).thenReturn(mockUser);
        User result = userService.getByEmail(mockUser.getUsername());
        // Assert
        Assertions.assertEquals(mockUser , result);
    }

}
