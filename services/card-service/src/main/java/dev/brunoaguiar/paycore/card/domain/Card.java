package dev.brunoaguiar.paycore.card.domain;

import java.time.LocalDate;
import java.time.YearMonth;

public class Card {

    private final YearMonth expiration;
    private CardStatus status = CardStatus.ACTIVE;

    public Card(YearMonth expiration) {
        this.expiration = expiration;
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
