package org.howard.edu.lsp.assignment3;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.howard.edu.lsp.assignment3.model.Row;
import org.howard.edu.lsp.assignment3.util.CsvUtils;

/**
 * Writes a list of rows back to CSV using the provided header order.
 */
public class Loader {

    public static void writeCsv(Path path, List<String> headers, List<Row> rows) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
// header
            for (int i = 0; i < headers.size(); i++) {
                if (i > 0) {
                    bw.write(',');
                }
                bw.write(CsvUtils.toCsvField(headers.get(i)));
            }
            bw.write("\n");
// rows
            for (Row row : rows) {
                Map<String, String> map = row.asMap();
                for (int i = 0; i < headers.size(); i++) {
                    if (i > 0) {
                        bw.write(',');
                    }
                    String val = map.getOrDefault(headers.get(i), "");
                    bw.write(CsvUtils.toCsvField(val));
                }
                bw.write("\n");
            }
        }
    }
}
