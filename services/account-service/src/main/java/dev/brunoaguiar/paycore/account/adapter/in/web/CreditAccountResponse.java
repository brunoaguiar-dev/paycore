package dev.brunoaguiar.paycore.account.adapter.in.web;

import dev.brunoaguiar.paycore.account.domain.CreditAccount;

import java.math.BigDecimal;
import java.util.UUID;

public record CreditAccountResponse(UUID id, UUID cardId, BigDecimal creditLimit, BigDecimal availableCredit) {

    static CreditAccountResponse from(CreditAccount account) {
        return new CreditAccountResponse(
                account.id(),
                account.cardId(),
                account.creditLimit().amount(),
                account.availableCredit().amount());
    }
}
