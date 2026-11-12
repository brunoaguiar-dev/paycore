package dev.brunoaguiar.paycore.card.adapter.out.persistence;

import dev.brunoaguiar.paycore.card.TestcontainersConfiguration;
import dev.brunoaguiar.paycore.card.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.time.YearMonth;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class CardPersistenceAdapterTest {

    @Autowired
    private CardRepository cardRepository;

    @Test
    void savedCardCanFoundById() {
        Card card = new Card(new CardNumber("4111111111111234"), new HolderName("MARIA SILVA"), YearMonth.of(2030, 12));
        card.block();

        cardRepository.save(card);
        Optional<Card> found = cardRepository.findById(card.id());

        assertThat(found).isPresent();
        Card foundCard = found.get();
        assertThat(foundCard.number()).isEqualTo(card.number());
        assertThat(foundCard.holderName().value()).isEqualTo("MARIA SILVA");
        assertThat(foundCard.expiration()).isEqualTo(YearMonth.of(2030, 12));
        assertThat(foundCard.status()).isEqualTo(CardStatus.BLOCKED);
    }

    @Test
    void findByIdReturnsEmptyWhenCardDoesNotExist() {
        Optional<Card> found = cardRepository.findById(UUID.randomUUID());

        assertThat(found).isEmpty();
    }
}
