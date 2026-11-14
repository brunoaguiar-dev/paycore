package dev.brunoaguiar.paycore.account.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record Money(BigDecimal amount) {

    public Money {
        if (amount == null) {
            throw new IllegalArgumentException("O valor não pode ser nulo");
        }
        if (amount.signum() < 0) {
            throw new IllegalArgumentException("O valor não pode ser negativo");
        }
        amount = amount.setScale(2, RoundingMode.HALF_EVEN);
    }

    public static Money of(String amount) {
        return new Money(new BigDecimal(amount));
    }

    public boolean isZero() {
        return amount.signum() == 0;
    }
}
