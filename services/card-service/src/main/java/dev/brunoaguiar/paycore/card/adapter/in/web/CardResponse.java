package dev.brunoaguiar.paycore.card.adapter.in.web;

import dev.brunoaguiar.paycore.card.domain.Card;
import dev.brunoaguiar.paycore.card.domain.CardStatus;

import java.util.UUID;

public record CardResponse(UUID id, String number, String holderName, String expiration, CardStatus status) {

    static CardResponse from(Card card) {
        return new CardResponse(
                card.id(),
                card.number().toString(),
                card.holderName(),
                card.expiration().toString(),
                card.status());
    }
}
