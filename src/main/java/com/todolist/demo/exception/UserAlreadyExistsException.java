package com.todolist.demo.exception;

public class UserAlreadyExistsException extends RuntimeException {
    private final String message;

    public UserAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
