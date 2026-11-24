package dev.brunoaguiar.paycore.account.adapter.out.persistence;

import dev.brunoaguiar.paycore.account.TestcontainersConfiguration;
import dev.brunoaguiar.paycore.account.domain.CreditAccount;
import dev.brunoaguiar.paycore.account.domain.CreditAccountRepository;
import dev.brunoaguiar.paycore.account.domain.Money;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
class CreditAccountPersistenceAdapterTest {

    @Autowired
    private CreditAccountRepository creditAccountRepository;

    @Test
    void savedAccountCanBeFoundById() {
        // Crédito disponível diferente do limite, para conferir que os dois voltam separados do banco.
        CreditAccount account = CreditAccount.restore(
                UUID.randomUUID(), UUID.randomUUID(), Money.of("5000.00"), Money.of("1200.50"));

        creditAccountRepository.save(account);
        Optional<CreditAccount> found = creditAccountRepository.findById(account.id());

        assertThat(found).isPresent();
        CreditAccount foundAccount = found.get();
        assertThat(foundAccount.cardId()).isEqualTo(account.cardId());
        assertThat(foundAccount.creditLimit()).isEqualTo(Money.of("5000.00"));
        assertThat(foundAccount.availableCredit()).isEqualTo(Money.of("1200.50"));
    }

    @Test
    void findByIdReturnsEmptyWhenAccountDoesNotExist() {
        Optional<CreditAccount> found = creditAccountRepository.findById(UUID.randomUUID());

        assertThat(found).isEmpty();
    }

    @Test
    void savedAccountCanBeFoundByCardId() {
        CreditAccount account = new CreditAccount(UUID.randomUUID(), Money.of("3000.00"));

        creditAccountRepository.save(account);
        Optional<CreditAccount> found = creditAccountRepository.findByCardId(account.cardId());

        assertThat(found).isPresent();
        assertThat(found.get().id()).isEqualTo(account.id());
    }
}
