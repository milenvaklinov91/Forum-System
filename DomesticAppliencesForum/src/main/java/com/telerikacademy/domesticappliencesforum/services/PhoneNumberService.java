package com.telerikacademy.domesticappliencesforum.services;

import com.telerikacademy.domesticappliencesforum.models.PhoneNumber;

public interface PhoneNumberService {
    PhoneNumber getPhoneNumberById(int id);

    void createPhoneNumber(PhoneNumber phoneNumber);

    void isDuplicatePhoneNumber(PhoneNumber phoneNumber);

}
