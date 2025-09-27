package org.howard.edu.lsp.assignment3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.howard.edu.lsp.assignment3.Extractor.ExtractResult;
import org.howard.edu.lsp.assignment3.model.Row;
import org.howard.edu.lsp.assignment3.rules.CategoryMapRule;
import org.howard.edu.lsp.assignment3.rules.PriceNormalizeRule;
import org.howard.edu.lsp.assignment3.rules.PriceRangeRule;
import org.howard.edu.lsp.assignment3.rules.TitleCaseRule;

public class ETLMain {
    public static void main(String[] args) {
        Path in = Path.of("data", "products.csv"); 
        Path out = Path.of("data", "transformed_products.csv");

        int rowsRead = 0;
        int rowsTransformed = 0;
        int rowsSkipped = 0;

        // Step 1: Check if the input file exists
        if (!Files.exists(in)) {
            System.err.println("Error: Input file not found: " + in);
            System.exit(1);  // Exit if the file is missing
        }

        try {
            // Step 2: Extract data
            ExtractResult er = Extractor.readCsv(in);
            rowsRead = er.rows.size();
            System.out.println("Headers: " + er.headers);

            if (rowsRead == 0) {
                System.out.println("Warning: No data rows found. Creating output file with just the header.");
                Loader.writeCsv(out, er.headers, er.rows);
                System.out.println("ETL completed. Wrote: " + out);
                System.exit(0);  // Exit early since no data to process
            }

            // Print rows before transformation
            for (Row row : er.rows) {
                System.out.println("Row before transformation: " + row.asMap());
            }

            // Step 3: Create transformation pipeline
            Map<String, String> catMap = new HashMap<>();
            catMap.put("ELEC", "Electronics");
            catMap.put("HOME", "Home");

            Transformer tx = new Transformer()
                .add(new TitleCaseRule("Name"))  // Uppercase name
                .add(new PriceNormalizeRule("Price"))  // Normalize price (apply discount)
                .add(new CategoryMapRule("Category", "Category", catMap))  // Re-categorize
                .add(new PriceRangeRule("Price"));  // Add PriceRange based on final price

            // Step 4: Apply transformations to rows
            for (Row row : er.rows) {
                try {
                    tx.applyAll(er.rows);
                    rowsTransformed++;
                } catch (Exception e) {
                    rowsSkipped++;  // If row fails transformation, increment skipped count
                    System.err.println("Error processing row: " + row.asMap());
                }
            }

            // Print rows after transformation
            for (Row row : er.rows) {
                System.out.println("Row after transformation: " + row.asMap());
            }

            if (!er.headers.contains("PriceRange")) {
                er.headers.add("PriceRange");
            }
            for (Row row : er.rows) {
                System.out.println("Row data: " + row.asMap());
            }
            // Step 5: Load transformed data into output CSV
            Loader.writeCsv(out, er.headers, er.rows);
            System.out.println("ETL completed. Wrote: " + out);

            // Step 6: Print run summary
            System.out.println("Run Summary:");
            System.out.println("Rows read: " + rowsRead);
            System.out.println("Rows transformed: " + rowsTransformed);
            System.out.println("Rows skipped: " + rowsSkipped);
            System.out.println("Output file written to: " + out);

        } catch (IOException e) {
            System.err.println("I/O error: " + e.getMessage());
            System.exit(1);  // Exit on file reading/writing error
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            System.exit(1);  // Exit on unexpected error
        }
    }
}