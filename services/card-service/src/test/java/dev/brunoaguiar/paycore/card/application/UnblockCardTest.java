package dev.brunoaguiar.paycore.card.application;

import dev.brunoaguiar.paycore.card.domain.Card;
import dev.brunoaguiar.paycore.card.domain.CardNumber;
import dev.brunoaguiar.paycore.card.domain.CardStatus;
import dev.brunoaguiar.paycore.card.domain.HolderName;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UnblockCardTest {

    private final InMemoryCardRepository cardRepository = new InMemoryCardRepository();
    private final UnblockCard unblockCard = new UnblockCard(cardRepository);

    @Test
    void unblocksABlockedCard() {
        Card card = new Card(new CardNumber("4111111111111234"), new HolderName("MARIA SILVA"), YearMonth.of(2030, 12));
        card.block();
        cardRepository.save(card);

        unblockCard.execute(card.id());

        Card savedCard = cardRepository.findById(card.id()).orElseThrow();
        assertThat(savedCard.status()).isEqualTo(CardStatus.ACTIVE);
    }

    @Test
    void unblockingUnknownCardIsNotAllowed() {
        UUID unknownId = UUID.randomUUID();

        assertThatThrownBy(() -> unblockCard.execute(unknownId))
                .isInstanceOf(CardNotFoundException.class);
    }
}
