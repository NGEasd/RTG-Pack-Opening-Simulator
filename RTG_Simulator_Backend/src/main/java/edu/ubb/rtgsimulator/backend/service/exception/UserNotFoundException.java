package edu.ubb.rtgsimulator.backend.service.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String username) {
        super("User not found with username: " + username);
    }

    public UserNotFoundException(Long userID) {
        super("User not found with username: " + userID);
    }

    // lancolashoz
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
