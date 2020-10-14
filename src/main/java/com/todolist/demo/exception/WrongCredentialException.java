package com.todolist.demo.exception;

public class WrongCredentialException extends RuntimeException {
    private String message;

    public WrongCredentialException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
