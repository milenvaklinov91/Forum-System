package com.telerikacademy.domesticappliencesforum.models;

import com.telerikacademy.domesticappliencesforum.models.enums.GenderTypes;

import javax.validation.constraints.NotNull;

public class Admin extends User {

    @NotNull(message = "Phone number can't be empty")
    private int phoneNumber;

    public Admin(String username, String firstName, String lastName, String email, String password,
                 GenderTypes gender, int phoneNumber) {
        super(username, firstName, lastName, email, password, gender);
        this.phoneNumber = phoneNumber;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}