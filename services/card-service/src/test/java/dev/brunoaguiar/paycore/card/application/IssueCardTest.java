package dev.brunoaguiar.paycore.card.application;

import dev.brunoaguiar.paycore.card.domain.Card;
import dev.brunoaguiar.paycore.card.domain.CardNumber;
import dev.brunoaguiar.paycore.card.domain.CardStatus;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.YearMonth;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

class IssueCardTest {

    private static final CardNumber NUMBER = new CardNumber("9999990000000000");

    private final InMemoryCardRepository cardRepository = new InMemoryCardRepository();
    private final Clock clock = Clock.fixed(Instant.parse("2026-11-15T10:00:00Z"), ZoneOffset.UTC);
    private final IssueCard issueCard = new IssueCard(cardRepository, () -> NUMBER, clock);

    @Test
    void issuedCardIsSavedWithGeneratedNumberAndHolder() {
        Card card = issueCard.execute("MARIA SILVA");

        Card savedCard = cardRepository.findById(card.id()).orElseThrow();
        assertThat(savedCard.number()).isEqualTo(NUMBER);
        assertThat(savedCard.holderName()).isEqualTo("MARIA SILVA");
        assertThat(savedCard.status()).isEqualTo(CardStatus.ACTIVE);
    }

    @Test
    void issuedCardExpiresInFiveYears() {
        Card card = issueCard.execute("MARIA SILVA");

        assertThat(card.expiration()).isEqualTo(YearMonth.of(2031, 11));
    }
}
