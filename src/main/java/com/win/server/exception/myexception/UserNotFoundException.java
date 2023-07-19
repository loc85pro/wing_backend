package com.win.server.exception.myexception;



public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException() {
        super();
    }
    public UserNotFoundException(String username) {
        super("Not found username: "+username);
    }
}
