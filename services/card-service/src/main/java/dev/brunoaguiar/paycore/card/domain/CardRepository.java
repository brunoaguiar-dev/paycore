package dev.brunoaguiar.paycore.card.domain;

import java.util.Optional;
import java.util.UUID;

public interface CardRepository {

    void save(Card card);

    Optional<Card> findById(UUID id);
}
