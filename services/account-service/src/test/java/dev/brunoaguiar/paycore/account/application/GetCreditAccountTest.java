package dev.brunoaguiar.paycore.account.application;

import dev.brunoaguiar.paycore.account.domain.CreditAccount;
import dev.brunoaguiar.paycore.account.domain.Money;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GetCreditAccountTest {

    private final InMemoryCreditAccountRepository creditAccountRepository = new InMemoryCreditAccountRepository();
    private final GetCreditAccount getCreditAccount = new GetCreditAccount(creditAccountRepository);

    @Test
    void returnsAnExistingAccount() {
        CreditAccount account = new CreditAccount(UUID.randomUUID(), Money.of("5000.00"));
        creditAccountRepository.save(account);

        CreditAccount foundAccount = getCreditAccount.execute(account.id());

        assertThat(foundAccount.id()).isEqualTo(account.id());

        assertThat(foundAccount.creditLimit()).isEqualTo(Money.of("5000.00"));
    }

    @Test
    void gettingUnknownAccountIsNotAllowed() {
        UUID unknownId = UUID.randomUUID();

        assertThatThrownBy(() -> getCreditAccount.execute(unknownId))
                .isInstanceOf(CreditAccountNotFoundException.class);
    }
}
