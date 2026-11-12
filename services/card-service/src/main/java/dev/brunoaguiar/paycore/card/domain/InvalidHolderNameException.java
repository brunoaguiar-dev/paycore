package dev.brunoaguiar.paycore.card.domain;

public class InvalidHolderNameException extends RuntimeException {

    public InvalidHolderNameException(String message) {
        super(message);
    }
}
