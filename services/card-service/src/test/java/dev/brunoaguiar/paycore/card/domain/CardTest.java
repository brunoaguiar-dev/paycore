package dev.brunoaguiar.paycore.card.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.YearMonth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CardTest {

    private Card newCard() {
        return newCardExpiringIn(YearMonth.of(2030, 12));
    }

    private Card newCardExpiringIn(YearMonth expiration) {
        return new Card(new CardNumber("4111111111111234"), "MARIA SILVA", expiration);
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

        Card card = new Card(number, "MARIA SILVA", YearMonth.of(2030, 12));

        assertThat(card.number()).isEqualTo(number);
        assertThat(card.holderName()).isEqualTo("MARIA SILVA");
    }

    @Test
    void cardWithoutNumberIsNotAllowed() {
        YearMonth expiration = YearMonth.of(2030, 12);

        assertThatThrownBy(() -> new Card(null, "MARIA SILVA", expiration))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void cardWithBlankHolderNameIsNotAllowed() {
        CardNumber number = new CardNumber("4111111111111234");
        YearMonth expiration = YearMonth.of(2030, 12);

        assertThatThrownBy(() -> new Card(number, " ", expiration))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void cardWithoutExpirationIsNotAllowed() {
        CardNumber number = new CardNumber("4111111111111234");

        assertThatThrownBy(() -> new Card(number, "MARIA SILVA", null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
