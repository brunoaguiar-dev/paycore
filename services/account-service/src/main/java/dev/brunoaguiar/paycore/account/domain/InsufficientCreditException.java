package dev.brunoaguiar.paycore.account.domain;

public class InsufficientCreditException extends RuntimeException {

    public InsufficientCreditException(Money availableCredit, Money amount) {
        super("Crédito insuficiente: disponível " + availableCredit.amount() + ", solicitado " + amount.amount());
    }
}
