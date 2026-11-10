package dev.brunoaguiar.paycore.card.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface CardJpaRepository extends JpaRepository<CardEntity, UUID> {
}
