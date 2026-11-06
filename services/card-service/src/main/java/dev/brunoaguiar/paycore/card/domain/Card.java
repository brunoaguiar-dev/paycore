package dev.brunoaguiar.paycore.card.domain;

import java.time.LocalDate;
import java.time.YearMonth;

public class Card {

    private final CardNumber number;
    private final String holderName;
    private final YearMonth expiration;
    private CardStatus status = CardStatus.ACTIVE;

    public Card(CardNumber number, String holderName, YearMonth expiration) {
        if (number == null) {
            throw new IllegalArgumentException("O número do cartão é obrigatório");
        }
        if (holderName == null || holderName.isBlank()) {
            throw new IllegalArgumentException("O nome do titular é obrigatório");
        }
        if (expiration == null) {
            throw new IllegalArgumentException("A validade do cartão é obrigatória");
        }
        this.number = number;
        this.holderName = holderName;
        this.expiration = expiration;
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
            throw new IllegalStateException("Cartão cancelado não pode ser bloqueado");
        }
        status = CardStatus.BLOCKED;
    }

    public void unblock() {
        if (status == CardStatus.CANCELED) {
            throw new IllegalStateException("Cartão cancelado não pode ser desbloqueado");
        }
        status = CardStatus.ACTIVE;
    }

    public void cancel() {
        status = CardStatus.CANCELED;
    }
}
