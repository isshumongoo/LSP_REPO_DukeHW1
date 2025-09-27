package org.howard.edu.lsp.assignment3.rules;

import org.howard.edu.lsp.assignment3.model.Row;

/**
 * Trims whitespace in selected columns (or all if columns is null/empty).
 */
public class TrimRule implements TransformationRule {

    private final String[] columns; // nullable => all columns

    public TrimRule(String... columns) {
        this.columns = columns;
    }

    @Override
    public void apply(Row row) {
        if (columns == null || columns.length == 0) {
            row.asMap().keySet().forEach(k -> row.put(k, row.get(k).trim()));
        } else {
            for (String c : columns) {
                row.put(c, row.get(c).trim());
            }
        }
    }
}
