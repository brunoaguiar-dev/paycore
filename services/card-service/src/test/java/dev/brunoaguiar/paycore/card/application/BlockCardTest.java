package dev.brunoaguiar.paycore.card.application;

import dev.brunoaguiar.paycore.card.domain.Card;
import dev.brunoaguiar.paycore.card.domain.CardNumber;
import dev.brunoaguiar.paycore.card.domain.CardStatus;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BlockCardTest {

    private final InMemoryCardRepository cardRepository = new InMemoryCardRepository();
    private final BlockCard blockCard = new BlockCard(cardRepository);

    @Test
    void blocksAnExistingCard() {
        Card card = new Card(new CardNumber("4111111111111234"), "MARIA SILVA", YearMonth.of(2030, 12));
        cardRepository.save(card);

        blockCard.execute(card.id());

        Card savedCard = cardRepository.findById(card.id()).orElseThrow();
        assertThat(savedCard.status()).isEqualTo(CardStatus.BLOCKED);
    }

    @Test
    void blockingUnknownCardIsNotAllowed() {
        UUID unknownId = UUID.randomUUID();

        assertThatThrownBy(() -> blockCard.execute(unknownId))
                .isInstanceOf(CardNotFoundException.class);
    }
}
