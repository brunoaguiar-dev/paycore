package dev.brunoaguiar.paycore.card.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CardNumberTest {

    @Test
    void numberWithLessThan16DigitsIsNotAllowed() {
        assertThatThrownBy(() -> new CardNumber("123"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void numberWithLettersIsNotAllowed() {
        assertThatThrownBy(() -> new CardNumber("abcdefghijklmnop"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void toStringShowsOnlyLastFourDigits() {
        CardNumber number = new CardNumber("4111111111111234");

        assertThat(number).hasToString("************1234");
    }
}
