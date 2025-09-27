package org.howard.edu.lsp.assignment3.rules;

import org.howard.edu.lsp.assignment3.model.Row;

/**
 * This rule adds a PriceRange field based on the final price of the product.
 */
public class PriceRangeRule implements TransformationRule {
    private final String priceColumn;

    public PriceRangeRule(String priceColumn) {
        this.priceColumn = priceColumn;
    }

    @Override
    public void apply(Row row) {
        String priceStr = row.get(priceColumn);
        System.out.println("Before PriceRange: " + priceStr);  // Debugging line
        try {
            double price = Double.parseDouble(priceStr);
            String priceRange;
            if (price <= 10.00) {
                priceRange = "Low";
            } else if (price <= 100.00) {
                priceRange = "Medium";
            } else if (price <= 500.00) {
                priceRange = "High";
            } else {
                priceRange = "Premium";
            }

            row.put("PriceRange", priceRange);
            System.out.println("After PriceRange: " + priceRange);  // Debugging line
        } catch (NumberFormatException e) {
            row.put("PriceRange", "Unknown");
        }
    }
}
