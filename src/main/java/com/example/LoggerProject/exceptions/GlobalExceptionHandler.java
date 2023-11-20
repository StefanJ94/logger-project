package com.example.LoggerProject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidUsernameException.class)
    public ErrorMessage invalidUsernameException(InvalidUsernameException e) {
        return createErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidEmailException.class)
    public ErrorMessage invalidEmailException(InvalidEmailException e) {
        return createErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidPasswordException.class)
    public ErrorMessage invalidPasswordException(InvalidPasswordException e) {
        return createErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidCredentialsException.class)
    public ErrorMessage invalidCredentialsException(InvalidCredentialsException e) {
        return createErrorMessage(e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    public ErrorMessage forbiddenException(ForbiddenException e) {
        return createErrorMessage(e.getMessage());
    }

    private ErrorMessage createErrorMessage(String message) {
        return ErrorMessage.builder().errorMessage(message).build();
    }
}