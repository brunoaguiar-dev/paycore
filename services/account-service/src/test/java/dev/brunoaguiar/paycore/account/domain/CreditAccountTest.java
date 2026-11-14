package dev.brunoaguiar.paycore.account.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreditAccountTest {

    @Test
    void newAccountHasAllCreditAvailable() {
        CreditAccount account = new CreditAccount(UUID.randomUUID(), Money.of("5000.00"));

        assertThat(account.creditLimit()).isEqualTo(Money.of("5000.00"));
        assertThat(account.availableCredit()).isEqualTo(Money.of("5000.00"));
    }

    @Test
    void AccountWithoutCardIsNotAllowed() {
        Money creditLimit = Money.of("5000.00");

        assertThatThrownBy(() -> new CreditAccount(null, creditLimit))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void accountWithZeroCreditLimitIsNotAllowed() {
        UUID cardId = UUID.randomUUID();
        Money creditLimit = Money.of("0.00");

        assertThatThrownBy(() -> new CreditAccount(cardId, creditLimit))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void restoredAccountKeepsIdAndAvailableCredit() {
        UUID id = UUID.randomUUID();
        UUID cardId = UUID.randomUUID();

        CreditAccount account = CreditAccount.restore(id, cardId, Money.of("5000.00"), Money.of("1200.00"));

        assertThat(account.id()).isEqualTo(id);
        assertThat(account.availableCredit()).isEqualTo(Money.of("1200.00"));
    }
}
