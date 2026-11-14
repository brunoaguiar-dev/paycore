package dev.brunoaguiar.paycore.account.domain;

import java.util.UUID;

public class CreditAccount {

    private final UUID id;
    private final UUID cardId;
    private final Money creditLimit;
    private Money availableCredit;

    public CreditAccount(UUID cardId, Money creditLimit) {
        this(UUID.randomUUID(), cardId, creditLimit, creditLimit);
    }

    private CreditAccount(UUID id, UUID cardId, Money creditLimit, Money availableCredit) {
        if (cardId == null) {
            throw new IllegalArgumentException("A conta precisa de um cartão");
        }
        if (creditLimit == null || creditLimit.isZero()) {
            throw new IllegalArgumentException("O limite de crédito deve ser maior que zero");
        }
        this.id = id;
        this.cardId = cardId;
        this.creditLimit = creditLimit;
        this.availableCredit = availableCredit;
    }

    public static CreditAccount restore(UUID id, UUID cardId, Money creditLimit, Money availableCredit) {
        return new CreditAccount(id, cardId, creditLimit, availableCredit);
    }

    public UUID id() {
        return id;
    }

    public UUID cardId() {
        return cardId;
    }

    public Money creditLimit() {
        return creditLimit;
    }

    public Money availableCredit() {
        return availableCredit;
    }
}
