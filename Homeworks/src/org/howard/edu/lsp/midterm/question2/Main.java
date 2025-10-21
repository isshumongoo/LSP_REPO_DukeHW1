package org.howard.edu.lsp.midterm.question2;

public class Main {
    public static void main(String[] args) {
        // Required exact output:
        System.out.println("Circle radius 3.0 \u2192 area = " + AreaCalculator.area(3.0));
        System.out.println("Rectangle 5.0 x 2.0 \u2192 area = " + AreaCalculator.area(5.0, 2.0));
        System.out.println("Triangle base 10, height 6 \u2192 area = " + AreaCalculator.area(10, 6));
        System.out.println("Square side 4 \u2192 area = " + AreaCalculator.area(4));

        // Exception demo (any message is fine). Trigger by invalid dimension:
        try {
            AreaCalculator.area(0);  // invalid square side; should throw
            System.out.println("ERROR: Exception was not thrown");
        } catch (IllegalArgumentException ex) {
            System.out.println("Caught IllegalArgumentException: " + ex.getMessage());
        }
    }

}
