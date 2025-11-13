package org.howard.edu.lsp.assignment6;

import java.util.ArrayList;
import java.util.List;

public class IntegerSet {
    private List<Integer> set = new ArrayList<Integer>();

    /**
     * Clears the internal representation of the set.
     */
    public void clear() {
        set.clear();
    }

    /**
     * Returns the number of elements in the set.
     */
    public int length() {
        return set.size();
    }

    /**
     * Returns true if the 2 sets are equal, false otherwise;
     * Two sets are equal if they contain all of the same values in ANY order.
     * This overrides the equals method from the Object class.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IntegerSet otherSet = (IntegerSet) o;
        return set.containsAll(otherSet.set) && otherSet.set.containsAll(set);
    }

    /**
     * Returns true if the set contains the value, otherwise false.
     */
    public boolean contains(int value) {
        return set.contains(value);
    }

    /**
     * Returns the largest item in the set (throws IllegalStateException if empty).
     */
    public int largest() {
        if (set.isEmpty()) {
            throw new IllegalStateException("Set is empty");
        }
        return set.stream().max(Integer::compare).get();
    }

    /**
     * Returns the smallest item in the set (throws IllegalStateException if empty).
     */
    public int smallest() {
        if (set.isEmpty()) {
            throw new IllegalStateException("Set is empty");
        }
        return set.stream().min(Integer::compare).get();
    }

    /**
     * Adds an item to the set or does nothing if already present.
     */
    public void add(int item) {
        if (!set.contains(item)) {
            set.add(item);
        }
    }

    /**
     * Removes an item from the set or does nothing if not there.
     */
    public void remove(int item) {
        set.remove(Integer.valueOf(item));
    }

    /**
     * Set union: modifies this to contain all unique elements in this or other.
     */
    public void union(IntegerSet other) {
        for (int item : other.set) {
            add(item);
        }
    }

    /**
     * Set intersection: modifies this to contain only elements in both sets.
     */
    public void intersect(IntegerSet other) {
        set.retainAll(other.set);
    }

    /**
     * Set difference (this \ other): modifies this to remove elements found in other.
     */
    public void diff(IntegerSet other) {
        set.removeAll(other.set);
    }

    /**
     * Set complement: modifies this to become (other \ this).
     */
    public void complement(IntegerSet other) {
        List<Integer> tempSet = new ArrayList<>(other.set);
        tempSet.removeAll(set);
        set = tempSet;
    }

    /**
     * Returns true if the set is empty, false otherwise.
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * Returns a String representation; overrides Object.toString().
     */
    @Override
    public String toString() {
        return set.toString();
    }
}
