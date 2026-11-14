package dev.brunoaguiar.paycore.account.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "credit_account")
class CreditAccountEntity {

    @Id
    private UUID id;

    private UUID cardId;

    private BigDecimal creditLimit;

    private BigDecimal availableCredit;

    protected CreditAccountEntity() {
    }

    CreditAccountEntity(UUID id, UUID cardId, BigDecimal creditLimit, BigDecimal availableCredit) {
        this.id = id;
        this.cardId = cardId;
        this.creditLimit = creditLimit;
        this.availableCredit = availableCredit;
    }

    UUID getId() {
        return id;
    }

    UUID getCardId() {
        return cardId;
    }

    BigDecimal getCreditLimit() {
        return creditLimit;
    }

    BigDecimal getAvailableCredit() {
        return availableCredit;
    }
}
