package dev.brunoaguiar.paycore.card.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LuhnTest {

    @Test
    void calculatesCheckDigit() {
        int checkDigit = Luhn.checkDigit("7992739871");

        assertThat(checkDigit).isEqualTo(3);
    }

    @Test
    void calculatesCheckDigitOfCardNumber() {
        int checkDigit = Luhn.checkDigit("411111111111111");

        assertThat(checkDigit).isEqualTo(1);
    }
}
