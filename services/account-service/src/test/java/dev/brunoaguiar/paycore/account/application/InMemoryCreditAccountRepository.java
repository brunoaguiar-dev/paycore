package dev.brunoaguiar.paycore.account.application;

import dev.brunoaguiar.paycore.account.domain.CreditAccount;
import dev.brunoaguiar.paycore.account.domain.CreditAccountRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

class InMemoryCreditAccountRepository implements CreditAccountRepository {

    private final Map<UUID, CreditAccount> accounts = new HashMap<>();

    @Override
    public void save(CreditAccount account) {
        accounts.put(account.id(), copy(account));
    }

    @Override
    public Optional<CreditAccount> findById(UUID id) {
        return Optional.ofNullable(accounts.get(id)).map(this::copy);
    }

    @Override
    public boolean existsByCardId(UUID cardId) {
        return accounts.values().stream()
                .anyMatch(account -> account.cardId().equals(cardId));
    }

    private CreditAccount copy(CreditAccount account) {
        return CreditAccount.restore(
                account.id(), account.cardId(), account.creditLimit(), account.availableCredit());
    }
}
