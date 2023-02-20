package com.assigment.api.handler;

import com.assigment.api.model.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<String> handleApplicationException(ApplicationException exception) {
        switch (exception.getType()) {
            case USER_NOT_FOUND:
            case INVALID_CREDENTIALS:
            case USER_ALREADY_EXISTS:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }

        return ResponseEntity.internalServerError().build();
    }
}
