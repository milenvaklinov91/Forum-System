package com.telerikacademy.domesticappliencesforum.controllers;

import com.telerikacademy.domesticappliencesforum.exceptions.EntityDuplicateException;
import com.telerikacademy.domesticappliencesforum.models.PhoneNumber;
import com.telerikacademy.domesticappliencesforum.models.TagTypes;
import com.telerikacademy.domesticappliencesforum.services.PhoneNumberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
@RestController
@RequestMapping("/api/phone-number")
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;

    public PhoneNumberController(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    @PostMapping
    public PhoneNumber create(@Valid @RequestBody PhoneNumber phoneNumber ) {
        try {
            phoneNumberService.createPhoneNumber(phoneNumber);
            return phoneNumber;
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }


}