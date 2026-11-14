package dev.brunoaguiar.paycore.account.domain;

import java.util.Optional;
import java.util.UUID;

public interface CreditAccountRepository {

    void save(CreditAccount account);

    Optional<CreditAccount> findById(UUID id);
}
