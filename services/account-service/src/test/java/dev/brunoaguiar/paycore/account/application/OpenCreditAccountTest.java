package dev.brunoaguiar.paycore.account.application;

import dev.brunoaguiar.paycore.account.domain.CreditAccount;
import dev.brunoaguiar.paycore.account.domain.Money;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OpenCreditAccountTest {

    private final InMemoryCreditAccountRepository creditAccountRepository = new InMemoryCreditAccountRepository();
    private final OpenCreditAccount openCreditAccount = new OpenCreditAccount(creditAccountRepository);

    @Test
    void opensAccountWithAllCreditAvailable() {
        UUID cardId = UUID.randomUUID();

        CreditAccount account = openCreditAccount.execute(cardId, Money.of("5000.00"));

        CreditAccount savedAccount = creditAccountRepository.findById(account.id()).orElseThrow();
        assertThat(savedAccount.cardId()).isEqualTo(cardId);

        assertThat(savedAccount.availableCredit()).isEqualTo(Money.of("5000.00"));
    }

    @Test
    void openingSecondAccountForSameCardIsNotAllowed() {
        UUID cardId = UUID.randomUUID();
        openCreditAccount.execute(cardId, Money.of("5000.00"));
        Money creditLimit = Money.of("3000.00");

        assertThatThrownBy(() -> openCreditAccount.execute(cardId, creditLimit))
                .isInstanceOf(CreditAccountAlreadyExistsException.class);
    }
}
