package dev.brunoaguiar.paycore.account.application;

import java.util.UUID;

public class CreditAccountAlreadyExistsException extends RuntimeException {

    public CreditAccountAlreadyExistsException(UUID cardId) {
        super("O cartão já tem uma conta de crédito: " + cardId);
    }
}
