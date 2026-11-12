package dev.brunoaguiar.paycore.card.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CardTest {

    private Card newCard() {
        return newCardExpiringIn(YearMonth.of(2030, 12));
    }

    private Card newCardExpiringIn(YearMonth expiration) {
        return new Card(new CardNumber("4111111111111234"), new HolderName("MARIA SILVA"), expiration);
    }

    @Test
    void newCardIsActive() {
        Card card = newCard();

        assertThat(card.status()).isEqualTo(CardStatus.ACTIVE);
    }

    @Test
    void blockingCardChangesStatusToBlocked() {
        Card card = newCard();

        card.block();

        assertThat(card.status()).isEqualTo(CardStatus.BLOCKED);
    }

    @Test
    void unblockingCardChangesStatusToActive() {
        Card card = newCard();
        card.block();

        card.unblock();

        assertThat(card.status()).isEqualTo(CardStatus.ACTIVE);
    }

    @Test
    void cancelingCardChangesStatusToCanceled() {
        Card card = newCard();

        card.cancel();

        assertThat(card.status()).isEqualTo(CardStatus.CANCELED);
    }

    @Test
    void unblockingCanceledCardIsNotAllowed() {
        Card card = newCard();
        card.cancel();

        assertThatThrownBy(card::unblock)
                .isInstanceOf(CanceledCardException.class);
    }

    @Test
    void blockingCanceledCardIsNotAllowed() {
        Card card = newCard();
        card.cancel();

        assertThatThrownBy(card::block)
                .isInstanceOf(CanceledCardException.class);
    }

    @Test
    void cardIsExpiredAfterExpirationMonth() {
        Card card = newCardExpiringIn(YearMonth.of(2030, 12));

        boolean expired = card.isExpiredOn(LocalDate.of(2031, 1, 1));

        assertThat(expired).isTrue();
    }

    @Test
    void cardIsNotExpiredOnLastDayOfExpirationMonth() {
        Card card = newCardExpiringIn(YearMonth.of(2030, 12));

        boolean expired = card.isExpiredOn(LocalDate.of(2030, 12, 31));

        assertThat(expired).isFalse();
    }

    @Test
    void cardKeepsNumberAndHolderName() {
        CardNumber number = new CardNumber("4111111111111234");

        Card card = new Card(number, new HolderName("MARIA SILVA"), YearMonth.of(2030, 12));

        assertThat(card.number()).isEqualTo(number);
        assertThat(card.holderName().value()).isEqualTo("MARIA SILVA");
    }

    @Test
    void cardWithoutNumberIsNotAllowed() {
        HolderName holderName = new HolderName("MARIA SILVA");
        YearMonth expiration = YearMonth.of(2030, 12);

        assertThatThrownBy(() -> new Card(null, holderName, expiration))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void cardWithoutHolderNameIsNotAllowed() {
        CardNumber number = new CardNumber("4111111111111234");
        YearMonth expiration = YearMonth.of(2030, 12);

        assertThatThrownBy(() -> new Card(number, null, expiration))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void cardWithoutExpirationIsNotAllowed() {
        CardNumber number = new CardNumber("4111111111111234");
        HolderName holderName = new HolderName("MARIA SILVA");

        assertThatThrownBy(() -> new Card(number, holderName, null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void newCardsHaveDifferentIds() {
        Card firstCard = newCard();
        Card secondCard = newCard();

        assertThat(firstCard.id()).isNotNull();
        assertThat(firstCard.id()).isNotEqualTo(secondCard.id());
    }

    @Test
    void restoredCardKeepsIdAndStatus() {
        UUID id = UUID.randomUUID();
        CardNumber number = new CardNumber("4111111111111234");
        YearMonth expiration = YearMonth.of(2030, 12);

        Card card = Card.restore(id, number, new HolderName("MARIA SILVA"), expiration, CardStatus.BLOCKED);

        assertThat(card.id()).isEqualTo(id);
        assertThat(card.status()).isEqualTo(CardStatus.BLOCKED);
    }
}
