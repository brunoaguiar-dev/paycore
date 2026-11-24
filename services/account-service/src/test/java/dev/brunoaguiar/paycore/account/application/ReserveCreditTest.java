package dev.brunoaguiar.paycore.account.application;

import dev.brunoaguiar.paycore.account.domain.CreditAccount;
import dev.brunoaguiar.paycore.account.domain.InsufficientCreditException;
import dev.brunoaguiar.paycore.account.domain.Money;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReserveCreditTest {

    private final InMemoryCreditAccountRepository creditAccountRepository = new InMemoryCreditAccountRepository();
    private final ReserveCredit reserveCredit = new ReserveCredit(creditAccountRepository);

    @Test
    void reservesCreditOnCardAccount() {
        CreditAccount account = new CreditAccount(UUID.randomUUID(), Money.of("5000.00"));
        creditAccountRepository.save(account);

        reserveCredit.execute(account.cardId(), Money.of("80.00"));

        CreditAccount savedAccount = creditAccountRepository.findById(account.id()).orElseThrow();
        assertThat(savedAccount.availableCredit()).isEqualTo(Money.of("4920.00"));
    }

    @Test
    void reservingForCardWithoutAccountIsNotAllowed() {
        UUID cardId = UUID.randomUUID();
        Money amount = Money.of("80.00");

        assertThatThrownBy(() -> reserveCredit.execute(cardId, amount))
                .isInstanceOf(CreditAccountNotFoundException.class);
    }

    @Test
    void insufficientCreditKeepsSavedAccountUnchanged() {
        CreditAccount account = new CreditAccount(UUID.randomUUID(), Money.of("5000.00"));
        creditAccountRepository.save(account);
        UUID cardId = account.cardId();
        Money amount = Money.of("6000.00");

        assertThatThrownBy(() -> reserveCredit.execute(cardId, amount))
                .isInstanceOf(InsufficientCreditException.class);

        CreditAccount savedAccount = creditAccountRepository.findById(account.id()).orElseThrow();
        assertThat(savedAccount.availableCredit()).isEqualTo(Money.of("5000.00"));
    }
}
