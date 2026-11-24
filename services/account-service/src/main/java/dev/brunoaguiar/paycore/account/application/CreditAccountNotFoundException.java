package dev.brunoaguiar.paycore.account.application;

import java.util.UUID;

public class CreditAccountNotFoundException extends RuntimeException {

    private CreditAccountNotFoundException(String message) {
        super(message);
    }

    public static CreditAccountNotFoundException forId(UUID accountId) {
        return new CreditAccountNotFoundException("Conta de crédito não encontrada: " + accountId);
    }

    public static CreditAccountNotFoundException forCard(UUID cardId) {
        return new CreditAccountNotFoundException("O cartão não tem conta de crédito: " + cardId);
    }
}
