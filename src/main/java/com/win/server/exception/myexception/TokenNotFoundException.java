package com.win.server.exception.myexception;

public class TokenNotFoundException extends RuntimeException{
    public TokenNotFoundException() {
        super();
    }
    public TokenNotFoundException(String message) {
        super(message);
    }
}
