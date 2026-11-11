package dev.brunoaguiar.paycore.card.domain;

public final class Luhn {

    private Luhn() {}

    public static int checkDigit(String digits) {
        int sum = 0;
        boolean shouldDouble = true;

        for (int i = digits.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(digits.charAt(i));

            if (shouldDouble) {
                digit = digit * 2;
                if (digit > 9) {
                    digit = digit - 9;
                }
            }

            sum = sum + digit;
            shouldDouble = !shouldDouble;
        }

        return (10 - sum % 10) % 10;
    }
}
