package edu.ubb.rtgsimulator.backend.repository.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
        super("Username already exists: " + username);
    }

    public UsernameAlreadyExistsException(Long userID) {
        super("Username already exists: " + userID);
    }

    // lancolashoz
    public UsernameAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
