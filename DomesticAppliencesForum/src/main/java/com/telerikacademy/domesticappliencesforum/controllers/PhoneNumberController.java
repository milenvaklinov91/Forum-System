package com.telerikacademy.domesticappliencesforum.controllers;

import com.telerikacademy.domesticappliencesforum.exceptions.AuthorizationException;
import com.telerikacademy.domesticappliencesforum.exceptions.EntityDuplicateException;
import com.telerikacademy.domesticappliencesforum.models.PhoneNumber;
import com.telerikacademy.domesticappliencesforum.models.User;
import com.telerikacademy.domesticappliencesforum.services.interfaces.PhoneNumberService;
import com.telerikacademy.domesticappliencesforum.services.interfaces.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/phone-number")
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;
    private final UserService userService;

    private final AuthenticationHelper authenticationHelper;

    public PhoneNumberController(PhoneNumberService phoneNumberService, UserService userService, AuthenticationHelper authenticationHelper) {
        this.phoneNumberService = phoneNumberService;
        this.userService = userService;
        this.authenticationHelper = authenticationHelper;
    }

    @PostMapping("/{id}")
    public PhoneNumber create(@RequestHeader HttpHeaders headers,
                              @Valid @RequestBody PhoneNumber phoneNumber, @PathVariable int id) {
        try {
            User user1 = authenticationHelper.tryGetUser(headers);
            User user = userService.getById(id);
            phoneNumberService.createPhoneNumber(phoneNumber, user);
            return phoneNumber;
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (
                AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}