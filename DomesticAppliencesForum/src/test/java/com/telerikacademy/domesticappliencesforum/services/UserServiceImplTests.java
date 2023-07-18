package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
    }
    @Test
    void getById_Should_ReturnUser_When_MatchExist(){
        User mockUser = createMockBerr();

        Mockito.when(mockRepository.getUserById(Mockito.anyInt())).thenReturn(mockUser);

        User result = userService.getById(mockUser.getId());

        Assertions.assertEquals(mockUser , result);

    }*/
}
