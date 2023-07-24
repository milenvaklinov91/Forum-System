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
import static jdk.internal.org.objectweb.asm.util.CheckClassAdapter.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

        assertEquals(mockPhoneNumber, result);
    }

    @Test
    void createPhoneNumber_Should_ThrowException_When_AdminUserAndDuplicateExists() {
        User mockAdmin = createMockAdmin();
        PhoneNumber mockPhoneNumber = createPhoneNumber();

        when(phoneNumberMockRepository.getPhoneNumberById(mockPhoneNumber.getPhoneNumberId()))
                .thenReturn(mockPhoneNumber);

        assertThrows(EntityDuplicateException.class, () -> phoneNumberService.createPhoneNumber(mockPhoneNumber, mockAdmin));
    }

}
