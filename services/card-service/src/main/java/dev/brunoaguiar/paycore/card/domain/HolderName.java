package dev.brunoaguiar.paycore.card.domain;

import java.text.Normalizer;
import java.util.Locale;

public record HolderName(String value) {

    private static final int MAX_LENGTH = 26;

    public HolderName {
        if (value == null || value.isBlank()) {
            throw new InvalidHolderNameException("O nome do titular é obrigatório");
        }
        value = normalize(value);

        if (!value.matches("[A-Z ]+")) {
            throw new InvalidHolderNameException("O nome do titular deve ter apenas letras");
        }
        if (!value.contains(" ")) {
            throw new InvalidHolderNameException("O nome do titular deve ter nome e sobrenome");
        }
        if (value.length() > MAX_LENGTH) {
            throw new InvalidHolderNameException("O nome do titular deve ter no máximo " + MAX_LENGTH + " caracteres");
        }
    }

    private static String normalize(String name) {
        String withoutAccents = Normalizer.normalize(name, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        return withoutAccents.trim()
                .replaceAll("\\s+", " ")
                .toUpperCase(Locale.ROOT);
    }
}
