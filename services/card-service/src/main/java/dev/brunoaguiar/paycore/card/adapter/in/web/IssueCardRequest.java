package dev.brunoaguiar.paycore.card.adapter.in.web;

import jakarta.validation.constraints.NotBlank;

public record IssueCardRequest(
        @NotBlank(message = "O nome do titular é obrigatório")
        String holderName) {
}
