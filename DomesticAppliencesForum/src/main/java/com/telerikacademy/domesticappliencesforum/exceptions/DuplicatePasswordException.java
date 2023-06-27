package com.telerikacademy.domesticappliencesforum.exceptions;

public class DuplicatePasswordException extends RuntimeException {
    public  DuplicatePasswordException(String password){
        super(String.format("New password cannot be the same as old password: %s",password));
    }
}
