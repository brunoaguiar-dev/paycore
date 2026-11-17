package dev.brunoaguiar.paycore.account.application;

import java.util.UUID;

public class CreditAccountNotFoundException extends RuntimeException {

    public CreditAccountNotFoundException(UUID accountId) {
        super("Conta de crédito não encontrada: " + accountId);
    }
}
