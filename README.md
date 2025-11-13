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


# ETL Pipeline Project Setup and Execution
## Step 1: Compile the Java Files

To compile the project, navigate to the root directory where the src folder is located and run the following javac command:

javac -d src/org/howard/edu/lsp/assignment3/bin src/org/howard/edu/lsp/assignment3/*.java src/org/howard/edu/lsp/assignment3/rules/*.java src/org/howard/edu/lsp/assignment3/model/*.java src/org/howard/edu/lsp/assignment3/util/*.java


-d flag specifies the output directory where the compiled .class files will be stored (src/org/howard/edu/lsp/assignment3/bin).

This command compiles all Java files in the assignment3, rules, model, and util directories.

## Step 2: Run the Program

Once the files are successfully compiled, you can run the program using the following java command:

java -cp src/org/howard/edu/lsp/assignment3/bin org.howard.edu.lsp.assignment3.ETLMain


The -cp option specifies the classpath to the directory containing the compiled .class files (src/org/howard/edu/lsp/assignment3/bin).

The command executes the main class ETLMain, which orchestrates the extraction, transformation, and loading (ETL) of the data.

# Assignment 6 — IntegerSet with JUnit Testing

Course: Large Scale Programming
Package: org.howard.edu.lsp.assignment6
Submission: IntegerSet.java, IntegerSetTest.java
(Optional) run.bat to automate compile + test run

## Overview

This assignment implements a reusable IntegerSet class using ArrayList<Integer> and validates all functionality with a full JUnit 5 test suite. The project focuses on correct set operations, exception handling, equality rules, and mutation behavior.

## Features
IntegerSet
Implements:
- add, remove, contains
- largest, smallest (with exceptions on empty sets)
- union, intersect, diff, complement
- clear, length, isEmpty
- Correct equals and toString overrides
- All mutators update this instance, as required

## JUnit Test Suite

IntegerSetTest.java includes:
- One test for every public method
- Extra tests for empty sets, duplicates, self-ops, and mutation safety
- Verified against all major edge cases
- All tests pass successfully.

## Running the Assignment
Manual

From the Homeworks folder (for me):

Compile:
javac -cp ".;lib\junit.jar" src\org\howard\edu\lsp\assignment6\IntegerSet.java src\org\howard\edu\lsp\assignment6\IntegerSetTest.java

Run tests:
java -jar lib\junit.jar --class-path src --scan-class-path

## Edge Cases Covered
- Empty sets
- Duplicate adds
- set.union(set) and set.diff(set)
- Ensuring operations never modify the other set