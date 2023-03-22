package com.example.demo.exceptions;

import java.io.Serial;

public class UserServiceException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 209507288353319104L;

    public UserServiceException(String message) {
        super(message);
    }
}
