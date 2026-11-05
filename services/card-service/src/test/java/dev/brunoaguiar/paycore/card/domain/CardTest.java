package dev.brunoaguiar.paycore.card.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CardTest {

    @Test
    void newCardIsActive() {
        Card card = new Card();

        assertThat(card.status()).isEqualTo(CardStatus.ACTIVE);
    }

    @Test
    void blockingCardChangesStatusToBlocked() {
        Card card = new Card();

        card.block();

        assertThat(card.status()).isEqualTo(CardStatus.BLOCKED);
    }

    @Test
    void unblockingCardChangesStatusToActive() {
        Card card = new Card();
        card.block();

        card.unblock();

        assertThat(card.status()).isEqualTo(CardStatus.ACTIVE);
    }

    @Test
    void cancelingCardChangesStatusToCanceled() {
        Card card = new Card();

        card.cancel();

        assertThat(card.status()).isEqualTo(CardStatus.CANCELED);
    }

    @Test
    void unblockingCanceledCardIsNotAllowed() {
        Card card = new Card();
        card.cancel();

        assertThatThrownBy(card::unblock)
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void blockingCanceledCardIsNotAllowed() {
        Card card = new Card();
        card.cancel();

        assertThatThrownBy(card::block)
                .isInstanceOf(IllegalStateException.class);
    }
}
