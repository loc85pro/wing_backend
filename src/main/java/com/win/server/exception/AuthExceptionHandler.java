package com.win.server.exception;

import com.win.server.exception.myexception.*;
import com.win.server.DTO.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;

import org.springframework.validation.BindException;
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
        return new ErrorResponse("Sao deo co token?", 401);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleExpiredToken(Exception ex, WebRequest request) {
        return new ErrorResponse("Expired Token", 401);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMalformedRequest(BindException ex, WebRequest request) {
        return new ErrorResponse(ex.getAllErrors().get(0).getDefaultMessage(), 400);
    }

    @ExceptionHandler(FileGeneralException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleFileError() {
        return new ErrorResponse("Catch error when handle your file", 500);
    }

    @ExceptionHandler(UnknownException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handdleUnknownError() {
        return new ErrorResponse("Something not right in server", 500);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound() {
        return new ErrorResponse("Not found", 404);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleForbidden() {
        return new ErrorResponse("Forbidden", 403);
    }
}
