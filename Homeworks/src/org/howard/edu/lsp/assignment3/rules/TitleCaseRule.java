package org.howard.edu.lsp.assignment3.rules;

import org.howard.edu.lsp.assignment3.model.Row;

/**
 * Converts a string column to Title Case (simple ASCII, space-delimited).
 */
public class TitleCaseRule implements TransformationRule {

    private final String column;

    public TitleCaseRule(String column) {
        this.column = column;
    }

    @Override
    public void apply(Row row) {
        String v = row.get(column);
        if (v.isEmpty()) {
            return;
        }
        System.out.println("Before TitleCase: " + v);  // Debugging line
        String[] parts = v.toLowerCase().split(" ");
        StringBuilder out = new StringBuilder();
        for (String p : parts) {
            out.append(Character.toUpperCase(p.charAt(0)));
            if (p.length() > 1) {
                out.append(p.substring(1));
            }
            out.append(' ');
        }
        row.put(column, out.toString().trim());
        System.out.println("After TitleCase: " + out.toString().trim());  // Debugging line
    }

}
