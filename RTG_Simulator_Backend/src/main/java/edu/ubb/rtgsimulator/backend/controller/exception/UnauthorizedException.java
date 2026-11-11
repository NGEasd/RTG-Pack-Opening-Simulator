package edu.ubb.rtgsimulator.backend.controller.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("User is unauthorized!");
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    // lancolashoz
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
