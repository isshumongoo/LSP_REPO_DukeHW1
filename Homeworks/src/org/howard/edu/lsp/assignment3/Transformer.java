package org.howard.edu.lsp.assignment3;


import java.util.ArrayList;
import java.util.List;
import org.howard.edu.lsp.assignment3.model.Row;
import org.howard.edu.lsp.assignment3.rules.TransformationRule;


/** Applies a list of TransformationRule steps to every row. */
public class Transformer {
    private final List<TransformationRule> pipeline = new ArrayList<>();

    public Transformer add(TransformationRule rule) {
        pipeline.add(rule);
        return this;
    }

    public void applyAll(List<Row> rows) {
        for (Row r : rows) {
            for (TransformationRule rule : pipeline) {
                rule.apply(r);  // Apply each transformation rule to the row
            }
        }
    }
}
