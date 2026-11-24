package dev.brunoaguiar.paycore.account.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface CreditAccountJpaRepository extends JpaRepository<CreditAccountEntity, UUID> {

    boolean existsByCardId(UUID cardId);

    Optional<CreditAccountEntity> findByCardId(UUID cardId);
}
