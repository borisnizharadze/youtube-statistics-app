package com.assigment.api.model.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ApplicationException extends RuntimeException {

    private ApplicationExceptionTypes type;

    public String getCode() {
        return type.name();
    }

    public String getMessage() {
        return type.getMessage();
    }
}
