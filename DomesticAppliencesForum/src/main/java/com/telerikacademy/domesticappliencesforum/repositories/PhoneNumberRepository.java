package com.telerikacademy.domesticappliencesforum.repositories;

import com.telerikacademy.domesticappliencesforum.models.PhoneNumber;

public interface PhoneNumberRepository {

    PhoneNumber getPhoneNumberById(int id);


    void createPhoneNumber(PhoneNumber phoneNumber);
}
