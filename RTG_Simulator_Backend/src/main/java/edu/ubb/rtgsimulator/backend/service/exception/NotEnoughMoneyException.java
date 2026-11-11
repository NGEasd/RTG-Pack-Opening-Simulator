package edu.ubb.rtgsimulator.backend.service.exception;

public class NotEnoughMoneyException extends RuntimeException {
    public NotEnoughMoneyException() {
        super("Cannot purchase, balance is too low!");
    }

    // lancolashoz
    public NotEnoughMoneyException(String message, Throwable cause) {
        super(message, cause);
    }
}
