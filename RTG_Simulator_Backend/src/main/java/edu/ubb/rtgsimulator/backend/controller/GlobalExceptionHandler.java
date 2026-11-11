package edu.ubb.rtgsimulator.backend.controller;

import edu.ubb.rtgsimulator.backend.controller.exception.UnauthorizedException;
import edu.ubb.rtgsimulator.backend.repository.exception.CardNotFoundException;
import edu.ubb.rtgsimulator.backend.repository.exception.CardNotOwnedException;
import edu.ubb.rtgsimulator.backend.repository.exception.RepositoryException;
import edu.ubb.rtgsimulator.backend.service.exception.InvalidPasswordException;
import edu.ubb.rtgsimulator.backend.service.exception.NotEnoughMoneyException;
import edu.ubb.rtgsimulator.backend.service.exception.UserNotFoundException;
import edu.ubb.rtgsimulator.backend.repository.exception.UsernameAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT) // 409
    public String alreadyExists(UsernameAlreadyExistsException e) {
        return e.getMessage();
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public String userNotFound(UserNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    public String invalidPassword(InvalidPasswordException e) {
        return e.getMessage();
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    public String invalidPassword(UnauthorizedException e) {
        return e.getMessage();
    }

    @ExceptionHandler(NotEnoughMoneyException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403 (or 400 Bad Request?)
    public String notEnoughMoney(NotEnoughMoneyException e) {
        return e.getMessage();
    }

    @ExceptionHandler(CardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String cardNotFound(CardNotOwnedException e) {return e.getMessage();}

    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String internalServerError(RepositoryException e) {
        return e.getMessage();
    }

}
