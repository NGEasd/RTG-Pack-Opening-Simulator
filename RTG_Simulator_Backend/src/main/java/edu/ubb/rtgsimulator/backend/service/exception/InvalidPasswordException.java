package edu.ubb.rtgsimulator.backend.service.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String username) {
        super("Incorrect password!");
    }

    public InvalidPasswordException(Long userID) {
        super("Incorrect password!");
    }

    // lancolashoz
    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
