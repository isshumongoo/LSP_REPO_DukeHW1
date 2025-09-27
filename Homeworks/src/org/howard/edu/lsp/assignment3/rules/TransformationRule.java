package org.howard.edu.lsp.assignment3.rules;


import org.howard.edu.lsp.assignment3.model.Row;


/** A single transformation step applied to a Row. */
public interface TransformationRule {
/** Mutates the provided row in place. */
void apply(Row row);
}