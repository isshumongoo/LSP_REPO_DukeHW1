package org.howard.edu.lsp.assignment3.rules;

import java.util.Map;
import org.howard.edu.lsp.assignment3.model.Row;

/**
 * Maps raw category codes/labels to canonical labels using a provided map.
 */
public class CategoryMapRule implements TransformationRule {
    private final String sourceColumn;
    private final String targetColumn;
    private final Map<String, String> mapping;

    public CategoryMapRule(String sourceColumn, String targetColumn, Map<String, String> mapping) {
        this.sourceColumn = sourceColumn;
        this.targetColumn = targetColumn;
        this.mapping = mapping;
    }

    @Override
    public void apply(Row row) {
        String raw = row.get(sourceColumn);
        System.out.println("Before category map: " + raw);  // Debugging line
        String canon = mapping.getOrDefault(raw.toLowerCase(), raw);  // Normalize case
        row.put(targetColumn, canon);
        System.out.println("After category map: " + canon);  // Debugging line
    }
}
