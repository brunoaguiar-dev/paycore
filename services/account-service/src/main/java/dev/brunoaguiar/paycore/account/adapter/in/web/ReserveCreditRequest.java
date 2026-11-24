package dev.brunoaguiar.paycore.account.adapter.in.web;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

record ReserveCreditRequest(
        @NotNull(message = "O cartão é obrigatório")
        UUID cardId,

        @NotNull(message = "O valor da reserva é obrigatório")
        @Positive(message = "O valor da reserva deve ser maior que zero")
        BigDecimal amount) {
}
