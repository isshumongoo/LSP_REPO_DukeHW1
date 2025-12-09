package org.howard.edu.lsp.finale.question1;

/**
 * Defines a password generation algorithm.
 */
public interface PasswordAlgorithm {
    /**
     * Generate a password of the given length.
     * @param length desired password length
     * @return generated password
     */
    String generate(int length);
}
