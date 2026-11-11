package dev.brunoaguiar.paycore.card.application;

import dev.brunoaguiar.paycore.card.domain.Card;
import dev.brunoaguiar.paycore.card.domain.CardRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

class InMemoryCardRepository implements CardRepository {

    private final Map<UUID, Card> cards = new HashMap<>();

    @Override
    public void save(Card card) {
        cards.put(card.id(), copy(card));
    }

    @Override
    public Optional<Card> findById(UUID id) {
        return Optional.ofNullable(cards.get(id)).map(this::copy);
    }

    private Card copy(Card card) {
        return Card.restore(card.id(), card.number(), card.holderName(), card.expiration(), card.status());
    }
}
