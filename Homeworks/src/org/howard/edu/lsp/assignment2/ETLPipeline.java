package org.howard.edu.lsp.assignment2;

public class ETLPipeline {

    public static void main(String[] args) {
        System.out.println("ETL Pipeline starting...");
        String input  = "data/products.csv";
        String output = "data/transformed_products.csv";

        try {
            var rows = extract(input);                // may include header
            int read = dataRowCount(rows);            // count data rows (excludes header if present)
            var transformed = transform(rows);        // transformed data rows only (no header)
            int transformedCount = transformed.size();
            int skipped = Math.max(read - transformedCount, 0);

            load(output, transformed);                // writes header + rows

            System.out.printf(
                "Run summary:%n  rows read: %d%n  transformed: %d%n  skipped: %d%n  output: %s%n",
                read, transformedCount, skipped, output
            );
        } catch (java.io.FileNotFoundException e) {
            System.err.println("Error: Missing input file at " + input);
        } catch (java.io.IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
        }
    }

    /* ========================== EXTRACT ========================== */
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
                String[] cols = line.split(",", -1);           // keep empty fields
                for (int i = 0; i < cols.length; i++) cols[i] = cols[i].trim();
                rows.add(cols);                                 // may include header
            }
        }
        return rows;
    }

    /* ========================= TRANSFORM ========================= */
    /**
     * Applies the required transformations in this exact order:
     * 1) Uppercase Name
     * 2) If original Category == "Electronics", apply 10% discount to Price, then round HALF_UP to 2 decimals
     * 3) If final price > 500.00 AND original Category == "Electronics", set Category = "Premium Electronics"
     * 4) Compute PriceRange from final price
     *
     * Returns transformed rows only (no header).
     */
    private static java.util.List<String[]> transform(java.util.List<String[]> rows) {
        java.util.List<String[]> out = new java.util.ArrayList<>();
        if (rows == null || rows.isEmpty()) return out;

        // Detect header: ProductID,Name,Price,Category
        int start = hasHeader(rows) ? 1 : 0;

        for (int i = start; i < rows.size(); i++) {
            String[] r = rows.get(i);

            // Validate shape
            if (r.length != 4) { continue; }

            String productIdStr = r[0];
            String name         = r[1];
            String priceStr     = r[2];
            String category     = r[3];

            // Parse productId and price
            int productId;
            double price;
            try {
                productId = Integer.parseInt(productIdStr);
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException nfe) {
                continue; // skip invalid row
            }

            String originalCategory = category;

            // 1) Uppercase name
            name = upper(name);

            // 2) 10% discount for Electronics, then round HALF_UP to 2 decimals
            if ("Electronics".equalsIgnoreCase(originalCategory)) {
                price = round2HalfUp(price * 0.90);
            }

            // 3) Recategorize if final price > 500 AND original was Electronics
            if (price > 500.00 && "Electronics".equalsIgnoreCase(originalCategory)) {
                category = "Premium Electronics";
            }

            // 4) PriceRange from final price
            String priceRange = computePriceRange(price);

            // Build final row (no header here; load() writes header)
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

    /* =========================== LOAD =========================== */
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

    /* ========================= HELPERS ========================== */

    /** Count data rows, excluding header if present. */
    private static int dataRowCount(java.util.List<String[]> rows) {
        if (rows == null || rows.isEmpty()) return 0;
        int start = hasHeader(rows) ? 1 : 0;
        return Math.max(rows.size() - start, 0);
    }

    /** Detect if the first row is the expected header. */
    private static boolean hasHeader(java.util.List<String[]> rows) {
        if (rows == null || rows.isEmpty()) return false;
        String[] first = rows.get(0);
        if (first.length < 4) return false;
        return first[0].equalsIgnoreCase("ProductID")
            && first[1].equalsIgnoreCase("Name")
            && first[2].equalsIgnoreCase("Price")
            && first[3].equalsIgnoreCase("Category");
    }

    private static String upper(String s) {
        return s == null ? "" : s.toUpperCase();
    }

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
}
