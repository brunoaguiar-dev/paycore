package dev.brunoaguiar.paycore.card.adapter.out.number;

import dev.brunoaguiar.paycore.card.domain.CardNumber;
import dev.brunoaguiar.paycore.card.domain.CardNumberGenerator;
import dev.brunoaguiar.paycore.card.domain.Luhn;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RandomCardNumberGenerator implements CardNumberGenerator {

    private static final String BIN = "999999";
    private static final int ACCOUNT_DIGITS = 9;

    private final SecureRandom random = new SecureRandom();

    @Override
    public CardNumber generate() {
        StringBuilder digits = new StringBuilder(BIN);

        for (int i = 0; i < ACCOUNT_DIGITS; i++) {
            digits.append(random.nextInt(10));
        }

        digits.append(Luhn.checkDigit(digits.toString()));

        return new CardNumber(digits.toString());
    }
}
