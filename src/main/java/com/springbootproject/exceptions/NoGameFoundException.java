package com.springbootproject.exceptions;

public class NoGameFoundException extends Exception {
    private String message;

    public NoGameFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
