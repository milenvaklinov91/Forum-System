package com.telerikacademy.domesticappliencesforum.exceptions;

public class UnauthorizedOperationException extends RuntimeException{
    public UnauthorizedOperationException(String message) {
        super(message);
    }
}
