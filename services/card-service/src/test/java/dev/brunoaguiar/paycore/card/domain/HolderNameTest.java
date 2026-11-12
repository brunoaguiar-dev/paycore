package dev.brunoaguiar.paycore.card.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class HolderNameTest {

    @Test
    void convertsToUppercaseWithoutAccents() {
        HolderName holderName = new HolderName("João da Silva");

        assertThat(holderName.value()).isEqualTo("JOAO DA SILVA");
    }

    @Test
    void removesExtraSpaces() {
        HolderName holderName = new HolderName("   maria    silva ");

        assertThat(holderName.value()).isEqualTo("MARIA SILVA");
    }

    @Test
    void nullNameIsNotAllowed() {
        assertThatThrownBy(() -> new HolderName(null))
                .isInstanceOf(InvalidHolderNameException.class);
    }

    @Test
    void blankNameIsNotAllowed() {
        assertThatThrownBy(() -> new HolderName("   "))
                .isInstanceOf(InvalidHolderNameException.class);
    }

    @Test
    void nameWithNumbersIsNotAllowed() {
        assertThatThrownBy(() -> new HolderName("Maria 123"))
                .isInstanceOf(InvalidHolderNameException.class);
    }

    @Test
    void nameWithoutSurnameIsNotAllowed() {
        assertThatThrownBy(() -> new HolderName("Maria"))
                .isInstanceOf(InvalidHolderNameException.class);
    }

    @Test
    void keepsNameUpTo26Characters() {
        HolderName holderName = new HolderName("Maria da Silva Oliveira");

        assertThat(holderName.value()).isEqualTo("MARIA DA SILVA OLIVEIRA");
    }

    @Test
    void nameLongerThan26CharactersIsNotAllowed() {
        assertThatThrownBy(() -> new HolderName("Maria da Silva Oliveira Santos"))
                .isInstanceOf(InvalidHolderNameException.class);
    }
}
