package dev.brunoaguiar.paycore.card.adapter.out.persistence;

import dev.brunoaguiar.paycore.card.domain.CardStatus;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "card")
class CardEntity {

    @Id
    private UUID id;

    private String number;

    private String holderName;

    private LocalDate expiration;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    protected CardEntity() {}

    public CardEntity(UUID id, String number, String holderName, LocalDate expiration, CardStatus status) {
        this.id = id;
        this.number = number;
        this.holderName = holderName;
        this.expiration = expiration;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getHolderName() {
        return holderName;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    public CardStatus getStatus() {
        return status;
    }
}
