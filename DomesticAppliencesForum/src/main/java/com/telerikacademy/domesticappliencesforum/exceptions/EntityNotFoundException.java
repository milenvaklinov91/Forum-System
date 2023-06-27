package com.telerikacademy.domesticappliencesforum.exceptions;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String user, int id) {
        this(user, String.valueOf(id));
    }

    public EntityNotFoundException(String user, String value) {
        super(String.format("%s with ID %s not found.", user, value));
    }
}
