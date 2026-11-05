package dev.brunoaguiar.paycore.card.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CardTest {

    private Card newCard() {
        return new Card(YearMonth.of(2030, 12));
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
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void blockingCanceledCardIsNotAllowed() {
        Card card = newCard();
        card.cancel();

        assertThatThrownBy(card::block)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void cardIsExpiredAfterExpirationMonth() {
        Card card = new Card(YearMonth.of(2030, 12));

        boolean expired = card.isExpiredOn(LocalDate.of(2031, 1, 1));

        assertThat(expired).isTrue();
    }

    @Test
    void cardIsNotExpiredOnLastDayOfExpirationMonth() {
        Card card = new Card(YearMonth.of(2030, 12));

        boolean expired = card.isExpiredOn(LocalDate.of(2030, 12, 31));

        assertThat(expired).isFalse();
    }
}
