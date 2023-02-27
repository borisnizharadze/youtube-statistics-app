package com.assigment.api.model.exception;

import lombok.Getter;

@Getter
public enum ApplicationExceptionTypes {

    INTERNAL("internal"),
    INVALID_CREDENTIALS("invalid credentials"),
    USER_NOT_FOUND("user not found"),
    USER_ALREADY_EXISTS("user already exists");

    private final String message;

    ApplicationExceptionTypes(String message) {
        this.message = message;
    }
}
