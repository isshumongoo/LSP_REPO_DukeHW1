package org.howard.edu.lsp.finale.question1;

import java.security.SecureRandom;

/**
 * Generates passwords with letters only.
 */
public class LettersPasswordAlgorithm implements PasswordAlgorithm {
    private static final String LETTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz";

    private SecureRandom secure = new SecureRandom();

    @Override
    public String generate(int length) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = secure.nextInt(LETTERS.length());
            sb.append(LETTERS.charAt(index));
        }
        return sb.toString();
    }
}
