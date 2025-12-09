package org.howard.edu.lsp.finale.question1;

public class Main {
    public static void main(String[] args) {
        // Get the Password Generator Service instance
        PasswordGeneratorService service = PasswordGeneratorService.getInstance();

        // Set algorithm to "enhanced" for generating passwords with letters and numbers
        service.setAlgorithm("enhanced");

        // Generate and print a password with 12 characters
        String password = service.generatePassword(12);
        System.out.println("Generated Password: " + password);
    }
}
