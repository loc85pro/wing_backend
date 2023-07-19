package com.win.server.exception.myexception;

public class IncorrectPasswordException extends RuntimeException{
    public IncorrectPasswordException() {
        super();
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }
}
