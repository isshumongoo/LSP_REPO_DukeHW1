package org.howard.edu.lsp.assignment6;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class IntegerSetTest {

    @Test
    public void testClear() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        set.clear();
        assertTrue(set.isEmpty(), "Set should be empty after clear");
    }

    @Test
    public void testLength() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        assertEquals(2, set.length(), "Length should be 2");
    }

    @Test
    public void testEquals() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);
        set2.add(1);

        assertTrue(set1.equals(set2), "Sets should be equal regardless of order");

        set2.add(3);
        assertFalse(set1.equals(set2), "Sets with different elements should not be equal");
    }

    @Test
    public void testContains() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        assertTrue(set.contains(1), "Set should contain the element 1");
        assertFalse(set.contains(2), "Set should not contain the element 2");
    }

    @Test
    public void testLargest() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        set.add(3);
        assertEquals(3, set.largest(), "Largest element should be 3");
        
        set.clear();
        assertThrows(IllegalStateException.class, () -> set.largest(), "Should throw exception if empty");
    }

    @Test
    public void testSmallest() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        set.add(3);
        assertEquals(1, set.smallest(), "Smallest element should be 1");
        
        set.clear();
        assertThrows(IllegalStateException.class, () -> set.smallest(), "Should throw exception if empty");
    }

    @Test
    public void testAdd() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        set.add(2);  // Duplicate, should not be added
        assertEquals(2, set.length(), "Set should contain only 2 unique elements");
    }

    @Test
    public void testRemove() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.remove(1);
        assertFalse(set.contains(1), "Set should not contain 1 after removal");
    }

    @Test
    public void testUnion() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);
        set2.add(3);

        set1.union(set2);
        assertTrue(set1.contains(1), "Set1 should contain 1 after union");
        assertTrue(set1.contains(2), "Set1 should contain 2 after union");
        assertTrue(set1.contains(3), "Set1 should contain 3 after union");
    }

    @Test
    public void testIntersect() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);
        set2.add(3);

        set1.intersect(set2);
        assertTrue(set1.contains(2), "Set1 should contain 2 after intersection");
        assertFalse(set1.contains(1), "Set1 should not contain 1 after intersection");
        assertFalse(set1.contains(3), "Set1 should not contain 3 after intersection");
    }

    @Test
    public void testDiff() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);
        set2.add(3);

        set1.diff(set2);
        assertFalse(set1.contains(2), "Set1 should not contain 2 after difference");
        assertTrue(set1.contains(1), "Set1 should contain 1 after difference");
    }

    @Test
    public void testComplement() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);
        set2.add(3);

        set1.complement(set2);
        assertTrue(set1.contains(3), "Set1 should contain 3 after complement");
        assertFalse(set1.contains(1), "Set1 should not contain 1 after complement");
        assertFalse(set1.contains(2), "Set1 should not contain 2 after complement");
    }

    @Test
    public void testIsEmpty() {
        IntegerSet set = new IntegerSet();
        assertTrue(set.isEmpty(), "Set should be empty initially");
        set.add(1);
        assertFalse(set.isEmpty(), "Set should not be empty after adding an element");
    }

    @Test
    public void testToString() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        set.add(3);
        assertEquals("[1, 2, 3]", set.toString(), "toString should return elements in square brackets");
    }

    @Test
    public void testUnionWithEmptySet() {
        IntegerSet set1 = new IntegerSet();
        IntegerSet set2 = new IntegerSet();

        set1.add(1);
        set1.add(2);

        set1.union(set2);  // union with empty
        assertEquals("[1, 2]", set1.toString());
    }

    @Test
    public void testIntersectWithEmptySet() {
        IntegerSet set1 = new IntegerSet();
        IntegerSet set2 = new IntegerSet();

        set1.add(1);
        set1.add(2);

        set1.intersect(set2);  // intersect with empty -> empty
        assertTrue(set1.isEmpty());
    }

    @Test
    public void testDiffWithEmptySet() {
        IntegerSet set1 = new IntegerSet();
        IntegerSet set2 = new IntegerSet();

        set1.add(1);
        set1.add(2);

        set1.diff(set2);  // A \ empty = A
        assertTrue(set1.contains(1));
        assertTrue(set1.contains(2));
        assertEquals(2, set1.length());
    }

    @Test
    public void testComplementWithEmptySet() {
        IntegerSet set1 = new IntegerSet();
        IntegerSet set2 = new IntegerSet();

        set2.add(1);
        set2.add(2);

        set1.complement(set2);  // empty complement relative to other is other
        assertTrue(set1.contains(1));
        assertTrue(set1.contains(2));
        assertEquals(2, set1.length());
    }

    @Test
    public void testAddDuplicateDoesNotChangeSet() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(1);
        set.add(1);

        assertEquals(1, set.length());
        assertTrue(set.contains(1));
    }

    @Test
    public void testSelfOperations() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);

        // union with self should not create duplicates
        set.union(set);
        assertEquals(2, set.length());
        assertTrue(set.contains(1));
        assertTrue(set.contains(2));

        // diff with self should make it empty
        set.diff(set);
        assertTrue(set.isEmpty());
    }

    @Test
    public void testUnionDoesNotModifyOther() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);

        set1.union(set2);

        // set2 should stay the same
        assertEquals(1, set2.length());
        assertTrue(set2.contains(2));
    }

    @Test
    public void testDiffDoesNotModifyOther() {
        IntegerSet set1 = new IntegerSet();
        set1.add(1);
        set1.add(2);

        IntegerSet set2 = new IntegerSet();
        set2.add(2);

        set1.diff(set2);

        // set2 must remain unchanged
        assertEquals(1, set2.length());
        assertTrue(set2.contains(2));
    }

}

