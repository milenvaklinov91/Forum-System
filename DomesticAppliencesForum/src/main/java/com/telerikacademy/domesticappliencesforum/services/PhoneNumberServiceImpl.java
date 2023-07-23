package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityDuplicateException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityNotFoundException;
import com.telerikacademy.domesticappliencesforum.models.PhoneNumber;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.repositories.interfaces.PhoneNumberRepository;
import com.telerikacademy.domesticappliencesforum.services.interfaces.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;
@Autowired
    public PhoneNumberServiceImpl(PhoneNumberRepository phoneNumberRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
    }

    @Override
    public PhoneNumber getPhoneNumberById(int id) {
        return phoneNumberRepository.getPhoneNumberById(id);
    }
    @Override
    public void createPhoneNumber(PhoneNumber phoneNumber, User user) {
        if (user.isAdmin()) {
            boolean duplicateExists = true;
            try {
                phoneNumberRepository.getPhoneNumberById(phoneNumber.getPhoneNumberId());
            } catch (EntityNotFoundException e) {
                duplicateExists = false;
            }
            if (duplicateExists) {
                throw new EntityDuplicateException("PhoneNumber", phoneNumber.getPhoneNumber());
            }
            phoneNumber.setAdmin(user);
            phoneNumberRepository.createPhoneNumber(phoneNumber);
        }

    }
}
