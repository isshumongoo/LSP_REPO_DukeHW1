package org.howard.edu.lsp.assignment3;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.howard.edu.lsp.assignment3.model.Row;
import org.howard.edu.lsp.assignment3.util.CsvUtils;

/**
 * Reads products.csv into a list of Row objects while preserving header order.
 */
public class Extractor {

    /**
     * Reads a CSV file at {@code path} and returns header + rows.
     */
    public static ExtractResult readCsv(Path path) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String headerLine = br.readLine();
            if (headerLine == null) {
                return new ExtractResult(new ArrayList<>(), new ArrayList<>());
            }
            List<String> headers = CsvUtils.parseLine(headerLine);
            List<Row> rows = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                List<String> fields = CsvUtils.parseLine(line);
                Map<String, String> map = new LinkedHashMap<>();
                for (int i = 0; i < headers.size(); i++) {
                    String key = headers.get(i);
                    String val = i < fields.size() ? fields.get(i) : "";
                    map.put(key, val);
                }
                rows.add(new Row(map));
            }
            return new ExtractResult(headers, rows);
        }
    }

    /**
     * Tuple of CSV headers and data rows.
     */
    public static class ExtractResult {

        public final List<String> headers; // in order
        public final List<Row> rows;

        public ExtractResult(List<String> headers, List<Row> rows) {
            this.headers = headers;
            this.rows = rows;
        }
    }
}
