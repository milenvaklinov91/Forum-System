package com.telerikacademy.domesticappliencesforum.services.interfaces;

import com.telerikacademy.domesticappliencesforum.models.PhoneNumber;
import com.telerikacademy.domesticappliencesforum.models.User;

public interface PhoneNumberService {
    PhoneNumber getPhoneNumberById(int id);

    void createPhoneNumber(PhoneNumber phoneNumber, User user);



}
