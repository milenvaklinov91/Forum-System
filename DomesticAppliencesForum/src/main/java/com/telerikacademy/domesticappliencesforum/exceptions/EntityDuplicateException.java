package com.telerikacademy.domesticappliencesforum.exceptions;

public class EntityDuplicateException extends RuntimeException {

    public EntityDuplicateException(String user, String username) {
        super(String.format("%s with username %s already exists.", user, username));
    }


}
