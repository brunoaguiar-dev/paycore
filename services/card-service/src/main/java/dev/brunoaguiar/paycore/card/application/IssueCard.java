package dev.brunoaguiar.paycore.card.application;

import dev.brunoaguiar.paycore.card.domain.Card;
import dev.brunoaguiar.paycore.card.domain.CardNumberGenerator;
import dev.brunoaguiar.paycore.card.domain.CardRepository;
import dev.brunoaguiar.paycore.card.domain.HolderName;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.YearMonth;

@Service
public class IssueCard {

    private static final int VALIDITY_YEARS = 5;

    private final CardRepository cardRepository;
    private final CardNumberGenerator cardNumberGenerator;
    private final Clock clock;

    public IssueCard(CardRepository cardRepository, CardNumberGenerator cardNumberGenerator, Clock clock) {
        this.cardRepository = cardRepository;
        this.cardNumberGenerator = cardNumberGenerator;
        this.clock = clock;
    }

    public Card execute(String holderName) {
        YearMonth expiration = YearMonth.now(clock).plusYears(VALIDITY_YEARS);
        Card card = new Card(cardNumberGenerator.generate(), new HolderName(holderName), expiration);
        cardRepository.save(card);
        return card;
    }
}
