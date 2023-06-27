package com.telerikacademy.domesticappliencesforum.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Admin {

    @NotNull( message = "Name can't be empty")
    @Size(min = 4, max = 32, message = "Name should be between 4 and 32 symbols")
    private String firstName;
    @NotNull(message = "Name can't be empty")
    @Size(min = 4, max = 32, message = "Name should be between 4 and 32 symbols")
    private String lastName;
    @NotNull(message = "Email can't be empty")
    @Email(message = "Email is invalid")
    private String email;
    @NotNull(message = "Phone number can't be empty")
    private int phoneNumber;

}
