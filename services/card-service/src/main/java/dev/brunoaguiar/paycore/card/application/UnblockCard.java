package dev.brunoaguiar.paycore.card.application;

import dev.brunoaguiar.paycore.card.domain.Card;
import dev.brunoaguiar.paycore.card.domain.CardRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UnblockCard {

    private final CardRepository cardRepository;

    public UnblockCard(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void execute(UUID cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));
        card.unblock();
        cardRepository.save(card);
    }
}
