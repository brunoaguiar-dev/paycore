package dev.brunoaguiar.paycore.card.domain;

public class Card {

    private CardStatus status = CardStatus.ACTIVE;

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
