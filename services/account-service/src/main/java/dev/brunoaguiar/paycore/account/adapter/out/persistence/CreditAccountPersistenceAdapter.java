package dev.brunoaguiar.paycore.account.adapter.out.persistence;

import dev.brunoaguiar.paycore.account.domain.CreditAccount;
import dev.brunoaguiar.paycore.account.domain.CreditAccountRepository;
import dev.brunoaguiar.paycore.account.domain.Money;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
class CreditAccountPersistenceAdapter implements CreditAccountRepository {

    private final CreditAccountJpaRepository jpaRepository;

    CreditAccountPersistenceAdapter(CreditAccountJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(CreditAccount account) {
        jpaRepository.save(toEntity(account));
    }

    @Override
    public Optional<CreditAccount> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public boolean existsByCardId(UUID cardId) {
        return jpaRepository.existsByCardId(cardId);
    }

    @Override
    public Optional<CreditAccount> findByCardId(UUID cardId) {
        return jpaRepository.findByCardId(cardId).map(this::toDomain);
    }

    private CreditAccountEntity toEntity(CreditAccount account) {
        return new CreditAccountEntity(
                account.id(),
                account.cardId(),
                account.creditLimit().amount(),
                account.availableCredit().amount());
    }

    private CreditAccount toDomain(CreditAccountEntity entity) {
        return CreditAccount.restore(
                entity.getId(),
                entity.getCardId(),
                new Money(entity.getCreditLimit()),
                new Money(entity.getAvailableCredit()));
    }
}
