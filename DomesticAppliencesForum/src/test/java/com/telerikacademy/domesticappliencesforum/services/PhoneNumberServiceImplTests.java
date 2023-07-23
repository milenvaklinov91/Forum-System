package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityDuplicateException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.PhoneNumber;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.PhoneNumberRepository;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.telerikacademy.domesticappliencesforum.services.Helper.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PhoneNumberServiceImplTests {
    @Mock
    PhoneNumberRepository phoneNumberMockRepository;
    @Mock
    UserRepository userMockRepository;
    @InjectMocks
    PhoneNumberServiceImpl phoneNumberService;



    @Test
    void getPhoneNumberById_Should_ReturnPhoneNumber_When_MatchExist() {
        PhoneNumber mockPhoneNumber = createPhoneNumber();

        when(phoneNumberMockRepository.getPhoneNumberById(Mockito.anyInt())).thenReturn(mockPhoneNumber);
        PhoneNumber result = phoneNumberService.getPhoneNumberById(mockPhoneNumber.getPhoneNumberId());

        Assertions.assertEquals(mockPhoneNumber, result);
    }

    @Test
    void createPhoneNumber_Should_ThrowException_When_AdminUserAndDuplicateExists() {
        User mockAdmin = createMockAdmin();
        PhoneNumber mockPhoneNumber = createPhoneNumber();

        when(phoneNumberMockRepository.getPhoneNumberById(mockPhoneNumber.getPhoneNumberId()))
                .thenReturn(mockPhoneNumber);

        assertThrows(EntityDuplicateException.class, () -> phoneNumberService.createPhoneNumber(mockPhoneNumber, mockAdmin));
    }

   /* @Test
    void createPhoneNumber_Should_CreatePhoneNumber_When_AdminUserAndNoDuplicate() {
        // ARRANGE
        User mockUserAdmin = createMockAdmin();
        PhoneNumber mockPhoneNumber = createPhoneNumber();


        when(phoneNumberMockRepository.getPhoneNumberById(mockPhoneNumber.getPhoneNumberId()))
                .thenThrow(EntityNotFoundException.class);

        // ACT
        phoneNumberService.createPhoneNumber(mockPhoneNumber, mockUserAdmin);

        // ASSERT
        verify(phoneNumberMockRepository, times(1)).createPhoneNumber(mockPhoneNumber);
    }*/

    /*@Test
    void createPhoneNumber_Should_CreatePhoneNumber_When_PhoneNumberCreated(){
        User mockUserAdmin = createMockAdmin();
        PhoneNumber mockPhoneNumber = createPhoneNumber();

        when(phoneNumberMockRepository.getPhoneNumberById(mockPhoneNumber.getPhoneNumberId()))
                .thenReturn(null);

        phoneNumberService.createPhoneNumber(mockPhoneNumber,mockUserAdmin);

        verify(phoneNumberMockRepository,times(1),createPhoneNumber(any());
    }*/
}
