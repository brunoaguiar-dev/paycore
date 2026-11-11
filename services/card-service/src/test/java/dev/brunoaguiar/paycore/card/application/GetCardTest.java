package dev.brunoaguiar.paycore.card.application;

import dev.brunoaguiar.paycore.card.domain.Card;
import dev.brunoaguiar.paycore.card.domain.CardNumber;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GetCardTest {

    private final InMemoryCardRepository cardRepository = new InMemoryCardRepository();
    private final GetCard getCard = new GetCard(cardRepository);

    @Test
    void returnsAnExistingCard() {
        Card card = new Card(new CardNumber("4111111111111234"), "MARIA SILVA", YearMonth.of(2030, 12));
        cardRepository.save(card);

        Card foundCard = getCard.execute(card.id());

        assertThat(foundCard.id()).isEqualTo(card.id());
        assertThat(foundCard.holderName()).isEqualTo("MARIA SILVA");
    }

    @Test
    void gettingUnknownCardIsNotAllowed() {
        UUID unknownId = UUID.randomUUID();

        assertThatThrownBy(() -> getCard.execute(unknownId))
                .isInstanceOf(CardNotFoundException.class);
    }
}
