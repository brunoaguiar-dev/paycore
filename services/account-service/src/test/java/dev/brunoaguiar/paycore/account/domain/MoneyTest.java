package dev.brunoaguiar.paycore.account.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoneyTest {

    @Test
    void sameValueWithDifferentScaleIsEqual() {
        assertThat(Money.of("100.5")).isEqualTo(Money.of("100.50"));
    }

    @Test
    void roundsToTwoDecimalPlacesUsingHalfEven() {
        assertThat(Money.of("10.005")).isEqualTo(Money.of("10.00"));
        assertThat(Money.of("10.015")).isEqualTo(Money.of("10.02"));
    }

    @Test
    void negativeAmountIsNotAllowed() {
        assertThatThrownBy(() -> Money.of("-1.00"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void subtractsAmounts() {
        Money result = Money.of("100.00").minus(Money.of("30.50"));

        assertThat(result).isEqualTo(Money.of("69.50"));
    }

    @Test
    void comparesAmounts() {
        assertThat(Money.of("100.00").isGreaterThan(Money.of("99.99"))).isTrue();
        assertThat(Money.of("100.00").isGreaterThan(Money.of("100.00"))).isFalse();
    }
}
