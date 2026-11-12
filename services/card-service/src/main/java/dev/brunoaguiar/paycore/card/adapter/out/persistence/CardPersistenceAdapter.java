package dev.brunoaguiar.paycore.card.adapter.out.persistence;

import dev.brunoaguiar.paycore.card.domain.Card;
import dev.brunoaguiar.paycore.card.domain.CardNumber;
import dev.brunoaguiar.paycore.card.domain.CardRepository;
import dev.brunoaguiar.paycore.card.domain.HolderName;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.Optional;
import java.util.UUID;

@Repository
class CardPersistenceAdapter implements CardRepository {

    private final CardJpaRepository jpaRepository;

    CardPersistenceAdapter(CardJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Card card) {
        jpaRepository.save(toEntity(card));
    }

    @Override
    public Optional<Card> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    private CardEntity toEntity(Card card) {
        return new CardEntity(
                card.id(),
                card.number().value(),
                card.holderName().value(),
                card.expiration().atDay(1),
                card.status());
    }

    private Card toDomain(CardEntity entity) {
        return Card.restore(
                entity.getId(),
                new CardNumber(entity.getNumber()),
                new HolderName(entity.getHolderName()),
                YearMonth.from(entity.getExpiration()),
                entity.getStatus());
    }
}
