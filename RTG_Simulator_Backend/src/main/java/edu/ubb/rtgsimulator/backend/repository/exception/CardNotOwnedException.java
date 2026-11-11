package edu.ubb.rtgsimulator.backend.repository.exception;

public class CardNotOwnedException extends RuntimeException {
    public CardNotOwnedException(String message) {
        super(message);
    }

    public CardNotOwnedException(String message, Throwable cause) {
        super(message, cause);
    }
}
