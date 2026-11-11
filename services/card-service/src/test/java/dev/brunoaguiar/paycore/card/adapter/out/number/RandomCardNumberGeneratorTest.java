package dev.brunoaguiar.paycore.card.adapter.out.number;

import dev.brunoaguiar.paycore.card.domain.CardNumber;
import dev.brunoaguiar.paycore.card.domain.Luhn;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RandomCardNumberGeneratorTest {

    private final RandomCardNumberGenerator generator = new RandomCardNumberGenerator();

    @Test
    void generatedNumberStartsWithPayCoreBin() {
        CardNumber number = generator.generate();

        assertThat(number.value()).startsWith("999999");
    }

    @Test
    void generatedNumberHasValidCheckDigit() {
        String value = generator.generate().value();

        int checkDigit = Luhn.checkDigit(value.substring(0, 15));

        assertThat(value).endsWith(String.valueOf(checkDigit));
    }
}
