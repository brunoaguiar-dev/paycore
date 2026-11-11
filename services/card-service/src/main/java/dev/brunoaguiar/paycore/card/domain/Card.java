package dev.brunoaguiar.paycore.card.domain;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.UUID;

public class Card {

    private final UUID id;
    private final CardNumber number;
    private final String holderName;
    private final YearMonth expiration;
    private CardStatus status;

    public Card(CardNumber number, String holderName, YearMonth expiration) {
        this(UUID.randomUUID(), number, holderName, expiration, CardStatus.ACTIVE);
    }

    private Card(UUID id, CardNumber number, String holderName, YearMonth expiration, CardStatus status) {
        if (number == null) {
            throw new IllegalArgumentException("O número do cartão é obrigatório");
        }
        if (holderName == null || holderName.isBlank()) {
            throw new IllegalArgumentException("O nome do titular é obrigatório");
        }
        if (expiration == null) {
            throw new IllegalArgumentException("A validade do cartão é obrigatória");
        }
        this.id = id;
        this.number = number;
        this.holderName = holderName;
        this.expiration = expiration;
        this.status = status;
    }

    public static Card restore(UUID id, CardNumber number, String holderName, YearMonth expiration, CardStatus status) {
        return new Card(id, number, holderName, expiration, status);
    }

    public UUID id() {
        return id;
    }

    public YearMonth expiration() {
        return expiration;
    }

    public CardNumber number() {
        return number;
    }

    public String holderName() {
        return holderName;
    }

    public boolean isExpiredOn(LocalDate date) {
        return YearMonth.from(date).isAfter(expiration);
    }

    public CardStatus status() {
        return status;
    }

    public void block() {
        if (status == CardStatus.CANCELED) {
            throw new CanceledCardException("Cartão cancelado não pode ser bloqueado");
        }
        status = CardStatus.BLOCKED;
    }

    public void unblock() {
        if (status == CardStatus.CANCELED) {
            throw new CanceledCardException("Cartão cancelado não pode ser desbloqueado");
        }
        status = CardStatus.ACTIVE;
    }

    public void cancel() {
        status = CardStatus.CANCELED;
    }
}
