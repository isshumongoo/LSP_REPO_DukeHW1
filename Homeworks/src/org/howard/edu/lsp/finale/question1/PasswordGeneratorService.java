package org.howard.edu.lsp.finale.question1;

/**
 * Central service for password generation.
 * Implements Singleton + Strategy patterns.
 */
public class PasswordGeneratorService {

    // Singleton instance
    private static PasswordGeneratorService instance = null;

    // Selected algorithm (Strategy)
    private PasswordAlgorithm algorithm = null;

    /**
     * Design Patterns Used:
     * 
     * 1. **Singleton Pattern**:
     *    - The Singleton pattern ensures that only one instance of the PasswordGeneratorService class is created.
     *    - This is appropriate because the service should be shared across the application, and we don't want to create multiple instances of it.
     *    - The getInstance() method ensures that only a single instance is created and provides a global access point to that instance.
     *    
     * 2. **Strategy Pattern**:
     *    - The Strategy pattern allows different password generation algorithms (basic, enhanced, letters) to be selected at runtime.
     *    - This is appropriate because the password generation logic needs to be flexible and easily interchangeable.
     *    - It allows the `PasswordGeneratorService` to be extended with new password generation strategies in the future without changing the existing code.
     *    - The setAlgorithm() method lets the client code select the algorithm to use at runtime.
     *    
     * Why These Patterns Are Appropriate:
     * 
     * - Singleton is used to provide a single shared instance of the `PasswordGeneratorService`, ensuring global access to the password generation functionality. This fits the requirement that there should be only one service instance throughout the application.
     * 
     * - Strategy allows the `PasswordGeneratorService` to support multiple password generation algorithms, making it easy to swap between different strategies without altering the service code. This ensures future expansion, as we can add new algorithms without affecting the existing structure.
     * 
     * Together, these patterns make the `PasswordGeneratorService` both **flexible** and **scalable**, fulfilling all requirements for multiple, swappable approaches to password generation while maintaining a simple, centralized service.
     */
    private PasswordGeneratorService() {}

    /**
     * Returns the singleton instance. Also getInstance() for public API
     */
    public static PasswordGeneratorService getInstance() {
        if (instance == null) {
            instance = new PasswordGeneratorService();
        }
        return instance;
    }

    /**
     * Selects the desired algorithm by name.
     */
    public void setAlgorithm(String name) {
        switch (name.toLowerCase()) {
            case "basic":
                this.algorithm = new BasicPasswordAlgorithm();
                break;

            case "enhanced":
                this.algorithm = new EnhancedPasswordAlgorithm();
                break;

            case "letters":
                this.algorithm = new LettersPasswordAlgorithm();
                break;

            default:
                throw new IllegalArgumentException("Unknown algorithm: " + name);
        }
    }

    /**
     * Generate a password. Must throw if algorithm not selected.
     */
    public String generatePassword(int length) {
        if (this.algorithm == null) {
            throw new IllegalStateException("Algorithm not set");
        }
        return this.algorithm.generate(length);
    }
}
