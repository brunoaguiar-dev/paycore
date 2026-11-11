package dev.brunoaguiar.paycore.card.domain;

public class CanceledCardException extends RuntimeException {

    public CanceledCardException(String message) {
        super(message);
    }
}
