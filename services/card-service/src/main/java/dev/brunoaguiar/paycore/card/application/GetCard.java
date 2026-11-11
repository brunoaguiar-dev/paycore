package dev.brunoaguiar.paycore.card.application;

import dev.brunoaguiar.paycore.card.domain.Card;
import dev.brunoaguiar.paycore.card.domain.CardRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetCard {

    private final CardRepository cardRepository;

    public GetCard(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card execute(UUID cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));
    }
}
