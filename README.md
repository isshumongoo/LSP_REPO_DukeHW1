<h1> Homework 1</h1>
<h2> The completed work compiles and completes the checklist</h2>
<h3> Bonus: for me in VS Code I added the run.bat file to automate the whole compling/running process</h3>

# Assignment 2 — CSV ETL Pipeline (Java)

**Course:** CSCI 363/540  
**Package:** `org.howard.edu.lsp.assignment2`  
**Input:** `data/products.csv`  
**Output:** `data/transformed_products.csv`  

## What the Program Does (Overview)
This program implements a simple **Extract–Transform–Load (ETL)** pipeline:

- **Extract**: reads a comma-delimited CSV from `data/products.csv` (first row is the header).
- **Transform (in this exact order)**:
  1. Uppercase `Name`.
  2. If `Category == "Electronics"`, apply a **10% discount**, then **round HALF_UP** to two decimals.
  3. If the **final price > 500.00** *and* the **original** category was `"Electronics"`, set `Category = "Premium Electronics"`.
  4. Compute `PriceRange` from the **final** price:  
     - `0.00–10.00` → **Low**  
     - `10.01–100.00` → **Medium**  
     - `100.01–500.00` → **High**  
     - `500.01+` → **Premium**
- **Load**: writes the transformed rows (with header) to `data/transformed_products.csv`.

The program prints a **run summary** at the end: rows read, transformed, skipped, and the output path.

---

## Project Structure

