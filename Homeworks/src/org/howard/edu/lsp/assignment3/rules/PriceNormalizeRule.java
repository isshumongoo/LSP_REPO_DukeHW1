package org.howard.edu.lsp.assignment3.rules;

import org.howard.edu.lsp.assignment3.model.Row;

public class PriceNormalizeRule implements TransformationRule {

    private final String column;

    public PriceNormalizeRule(String column) {
        this.column = column;
    }

    @Override
    public void apply(Row row) {
        String v = row.get(column);
        if (v == null || v.isEmpty()) {
            return;
        }

        String cleaned = v.replace("$", "").replace(",", "").trim();
        try {
            double d = Double.parseDouble(cleaned);

            // Check if category is Electronics and price is greater than 500
            String category = row.get("Category");  // Ensure the correct column name
            if ("Electronics".equalsIgnoreCase(category)) {
                if (d > 500) {
                    d = d * 0.9; // Apply 10% discount for standard Electronics
                    row.put("Category", "Premium Electronics");  // Update category to Premium Electronics
                    row.put(column, String.format("%.2f", d));  // Price remains the same (no discount)
                }
            }

            // Apply price normalization for all categories (if needed)
            // Update price with two decimal places
        } catch (NumberFormatException e) {
            // Handle parsing error
            System.err.println("Failed to parse price: " + v);
        }
    }
}
