package org.howard.edu.lsp.assignment3.util;

import java.util.ArrayList;
import java.util.List;

/** CSV utility methods for simple RFC4180-ish parsing/serialization. */
public final class CsvUtils {
    private CsvUtils() {
    }

    /**
     * Parse a single CSV line into fields (supports quotes and commas in quotes).
     */
    public static List<String> parseLine(String line) {
        List<String> out = new ArrayList<>();
        if (line == null)
            return out;
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    // Escaped quote
                    sb.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                out.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        out.add(sb.toString());
        return out;
    }

    /** Serialize a field for CSV output, quoting/escaping when needed. */
    public static String toCsvField(String s) {
        if (s == null) s = "";
            boolean needsQuotes = s.contains(",") || s.contains("\n") || s.contains("\r") || s.contains("\"");
        if (!needsQuotes) return s;
            return "\"" + s.replace("\"", "\"\"") + "\"";
    }
}
