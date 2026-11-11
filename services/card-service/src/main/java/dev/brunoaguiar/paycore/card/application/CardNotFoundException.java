package dev.brunoaguiar.paycore.card.application;

import java.util.UUID;

public class CardNotFoundException extends RuntimeException {

    public CardNotFoundException(UUID cardId) {
        super("Cartão não encontrado: " + cardId);
    }
}
