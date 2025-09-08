package org.howard.edu.lsp.assignment2;

public class ETLPipeline {
    public static void main(String[] args) {
        System.out.println("ETL Pipeline starting...");
        String input = "data/products.csv";
        String output = "data/transformed_products.csv";

        try {
            var rows = extract(input);
            var transformed = transform(rows);   // now implemented
            load(output, transformed);
            // (you'll add the summary counter printing next)
        } catch (java.io.FileNotFoundException e) {
            System.err.println("Error: Missing input file at " + input);
        } catch (java.io.IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
        }
    }

    private static java.util.List<String[]> extract(String inputPath) throws java.io.IOException {
        java.util.List<String[]> rows = new java.util.ArrayList<>();
        java.nio.file.Path path = java.nio.file.Path.of(inputPath);

        if (!java.nio.file.Files.exists(path)) {
            throw new java.io.FileNotFoundException();
        }

        try (var br = java.nio.file.Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] cols = line.split(",", -1);
                for (int i = 0; i < cols.length; i++) cols[i] = cols[i].trim();
                rows.add(cols); // includes header; transform() will skip it
            }
        }
        return rows;
    }

    /* ---------------------- NEW: TRANSFORM (per spec) ---------------------- */
    /**
     * Applies the required transformations in this exact order:
     * 1) Uppercase Name
     * 2) If original Category == "Electronics", apply 10% discount to Price, then round HALF_UP to 2 decimals
     * 3) If final price > 500.00 AND original Category == "Electronics", set Category = "Premium Electronics"
     * 4) Compute PriceRange from final price
     *
     * Input rows may include the header; this method detects and skips it.
     * Valid data rows must have exactly 4 fields: ProductID, Name, Price, Category.
     * Returns rows with 5 fields in order: ProductID,Name,Price,Category,PriceRange.
     */
    private static java.util.List<String[]> transform(java.util.List<String[]> rows) {
        java.util.List<String[]> out = new java.util.ArrayList<>();
        if (rows == null || rows.isEmpty()) return out;

        int start = 0;

        // Detect and skip header if present: ProductID,Name,Price,Category
        String[] first = rows.get(0);
        if (first.length >= 4) {
            String c0 = first[0].trim();
            String c1 = first[1].trim();
            String c2 = first[2].trim();
            String c3 = first[3].trim();
            if (c0.equalsIgnoreCase("ProductID")
                    && c1.equalsIgnoreCase("Name")
                    && c2.equalsIgnoreCase("Price")
                    && c3.equalsIgnoreCase("Category")) {
                start = 1;
            }
        }

        for (int i = start; i < rows.size(); i++) {
            String[] r = rows.get(i);
            // Validate shape
            if (r.length != 4) {
                continue; // skipped row (malformed)
            }

            String productIdStr = r[0];
            String name         = r[1];
            String priceStr     = r[2];
            String category     = r[3];

            // Parse productId and price; skip row if bad
            int productId;
            double price;
            try {
                productId = Integer.parseInt(productIdStr);
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException nfe) {
                continue; // skip invalid row
            }

            // Keep original category for rule #3
            String originalCategory = category;

            // 1) Uppercase name
            name = upper(name);

            // 2) Discount for Electronics, then round HALF_UP to 2 decimals
            if ("Electronics".equals(originalCategory)) {
                price = price * 0.90; // 10% off
                price = round2HalfUp(price);
            }
            // (Non-electronics: no change; will still format to two decimals when writing)

            // 3) Recategorize if final price > 500.00 AND originalCategory was Electronics
            if (price > 500.00 && "Electronics".equals(originalCategory)) {
                category = "Premium Electronics";
            }

            // 4) PriceRange from final price
            String priceRange = computePriceRange(price);

            // Build final row in required order; ensure two decimals in output
            String formattedPrice = String.format(java.util.Locale.ROOT, "%.2f", price);
            out.add(new String[] {
                String.valueOf(productId),
                name,
                formattedPrice,
                category,
                priceRange
            });
        }

        return out;
    }
    /* ---------------------------------------------------------------------- */

    private static String upper(String s) { return s == null ? "" : s.toUpperCase(); }

    private static double round2HalfUp(double value) {
        java.math.BigDecimal bd = java.math.BigDecimal.valueOf(value);
        bd = bd.setScale(2, java.math.RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private static String computePriceRange(double price) {
        if (price <= 10.00) return "Low";
        else if (price <= 100.00) return "Medium";
        else if (price <= 500.00) return "High";
        else return "Premium";
    }

    private static void load(String outputPath, java.util.List<String[]> rows) throws java.io.IOException {
        java.nio.file.Path out = java.nio.file.Path.of(outputPath);
        java.nio.file.Files.createDirectories(out.getParent());

        try (var bw = java.nio.file.Files.newBufferedWriter(out)) {
            // header (always)
            bw.write("ProductID,Name,Price,Category,PriceRange");
            bw.newLine();

            // write transformed rows
            for (String[] r : rows) {
                bw.write(String.join(",", r));
                bw.newLine();
            }
        }
    }
}
