package com.win.server.exception;

import com.win.server.exception.myexception.IncorrectPasswordException;
import com.win.server.exception.myexception.TokenNotFoundException;
import com.win.server.exception.myexception.UserNotFoundException;
import com.win.server.DTO.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleUserNotFound(Exception ex, WebRequest request) {
        return new ErrorResponse("User not found", 404);
    }

    @ExceptionHandler(IncorrectPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleIncorrectPassword(Exception ex, WebRequest request) {
        return new ErrorResponse("Password is incorrect", 401);
    }

    @ExceptionHandler(TokenNotFoundException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleTokenNotFound(Exception ex, WebRequest request) {
        return new ErrorResponse("Sao de'o co token?", 401);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleExpiredToken(Exception ex, WebRequest request) {
        return new ErrorResponse("Expired Token", 401);
    }
}
