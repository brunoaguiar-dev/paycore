package dev.brunoaguiar.paycore.card.domain;

public record CardNumber(String value) {

    public CardNumber {
        if (!value.matches("\\d{16}")) {
            throw new IllegalArgumentException("O número do cartão precisa ter exatamente 16 dígitos");
        }
    }

    @Override
    public String toString() {
        return "*".repeat(12) + value.substring(12);
    }
}
