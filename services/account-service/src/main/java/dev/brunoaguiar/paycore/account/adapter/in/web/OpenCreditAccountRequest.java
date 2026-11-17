package dev.brunoaguiar.paycore.account.adapter.in.web;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.UUID;

public record OpenCreditAccountRequest(
        @NotNull(message = "O cartão é obrigatório")
        UUID cardId,

        @NotNull(message = "O limite de crédito é obrigatório")
        @Positive(message = "O limite de crédito deve ser maior que zero")
        BigDecimal creditLimit) {
}
