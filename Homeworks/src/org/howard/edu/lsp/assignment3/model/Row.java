package org.howard.edu.lsp.assignment3.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A mutable, map-backed record representing one CSV row keyed by header names.
 * Keeps insertion order of columns via LinkedHashMap so we can preserve header
 * order on write.
 */
public class Row {

    private final LinkedHashMap<String, String> values;

    /**
     * Create a Row with the given header->value mapping.
     */
    public Row(Map<String, String> values) {
        this.values = new LinkedHashMap<>(values);
    }

    /**
     * Get a value by column name (returns empty string if missing).
     */
    public String get(String column) {
        return values.getOrDefault(column, "");
    }

    /**
     * Put/replace a value by column name.
     */
    public void put(String column, String value) {
        values.put(column, value);
    }

    /**
     * Immutable view of the internal map.
     */
    public Map<String, String> asMap() {
        return Collections.unmodifiableMap(values);
    }
}
