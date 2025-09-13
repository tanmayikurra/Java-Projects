package edu.ncsu.csc217.collections.list;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

/**
 * This class tests whether the SortedList class from 
 * the CSC217Collections library works.
 * 
 * Covers cases for empty lists, insertion/removal at front/middle/end, and duplicate/null input handling.
 * The list starts at 10 so the growth will be tested as well.
 * Author: Udita Pericharla
 */
public class SortedListTest {

	/**
	 * Tests the constructor of SortedList to validates that its able
	 *  to create an empty list.
	 */
	@Test
	public void testSortedList() {
		SortedList<String> list = new SortedList<String>();
		assertEquals(0, list.size());
		assertFalse(list.contains("apple"));
		
        String[] expected = new String[11];
        for (int i = 0; i < 11; i++) {
            expected[i] = "item" + i;
            list.add(expected[i]);
        }

        assertEquals(11, list.size());

        Arrays.sort(expected);

        for (int i = 0; i < 11; i++) {
            assertEquals("Mismatch at index " + i, expected[i], list.get(i));
        }
	}
    /**
     * Tests add(E) in the front, middle, end, null, and duplicates.
     */
	@Test
	public void testAdd() {
		SortedList<String> list = new SortedList<String>();
		
		list.add("banana");
		assertEquals(1, list.size());
		assertEquals("banana", list.get(0));
		
        // Below adds an element that sorts before "banana" (front)
        list.add("apple");
        assertEquals(2, list.size());
        assertEquals("apple", list.get(0));
        assertEquals("banana", list.get(1));

        // Below adds an element that sorts after "banana" (end)
        list.add("orange");
        assertEquals(3, list.size());
        assertEquals("orange", list.get(2));

        // Below adds an element that sorts between "banana" and "orange" (middle)
        list.add("blueberry");
        assertEquals(4, list.size());
        // should sort into apple, banana, blueberry, orange
        assertEquals("apple", list.get(0));
        assertEquals("banana", list.get(1));
        assertEquals("blueberry", list.get(2));
        assertEquals("orange", list.get(3));

        assertThrows(NullPointerException.class, () -> {
            list.add(null);
        });
        assertEquals(4, list.size());

        // Add duplicate "banana"—should throw IllegalArgumentException, and size remains 4
        assertThrows(IllegalArgumentException.class, () -> {
            list.add("banana");
        });
        assertEquals(4, list.size());
    }
	
    /**
     * Tests get(int) for edge cases: empty list, index < 0, index == size.
     */
	@Test
	public void testRemove() {
	    SortedList<String> list = new SortedList<>();

	    // Removing from an empty list should throw IndexOutOfBoundsException
	    assertThrows(IndexOutOfBoundsException.class, () -> {
	        list.remove(0);
	    });

	    list.add("banana");
	    list.add("apple");
	    list.add("date");
	    list.add("cherry");
	    assertEquals(4, list.size());

	    // Removing at a negative index 
	    assertThrows(IndexOutOfBoundsException.class, () -> {
	        list.remove(-1);
	    });

	    // Removing at index == size()
	    assertThrows(IndexOutOfBoundsException.class, () -> {
	        list.remove(list.size());
	    });


	    // Remove a middle element
	    String removed = list.remove(1);
	    assertEquals("banana", removed);
	    // Now list should be ["apple", "cherry", "date"]
	    assertEquals(3, list.size());
	    assertEquals("apple",  list.get(0));
	    assertEquals("cherry", list.get(1));
	    assertEquals("date",   list.get(2));

	    // Remove the last element
	    removed = list.remove(2);
	    assertEquals("date", removed);
	    // Now list should be ["apple", "cherry"]
	    assertEquals(2, list.size());
	    assertEquals("apple",  list.get(0));
	    assertEquals("cherry", list.get(1));

	    // Remove the first element
	    removed = list.remove(0);
	    assertEquals("apple", removed);
	    // Now list should be ["cherry"]
	    assertEquals(1, list.size());
	    assertEquals("cherry", list.get(0));
	}
	
	/**
	 * Tests indexOf(E) on empty list for present/absent elements, and null.
	 */
	@Test
	public void testIndexOf() {
	    SortedList<String> list = new SortedList<>();

	    // Empty list
	    assertEquals(-1, list.indexOf("apple"));

	    // Add elements
	    list.add("banana");
	    list.add("apple");
	    list.add("cherry");

	    // Now verify indices in sorted order
	    assertEquals(0, list.indexOf("apple"));
	    assertEquals(1, list.indexOf("banana"));
	    assertEquals(2, list.indexOf("cherry"));

	    // Absent element returns -1
	    assertEquals(-1, list.indexOf("date"));

	    // Passing null should throw NullPointerException
	    assertThrows(NullPointerException.class, () -> {
	        list.indexOf(null);
	    });
	}
	
    /**
     * Tests whether list becomes empty
     */
	@Test
	public void testClear() {
		SortedList<String> list = new SortedList<String>();

	    // Add some elements
	    list.add("apple");
	    list.add("banana");
	    list.add("cherry");
	    assertEquals(3, list.size());

	    // Clear the list
	    list.clear();
	    assertEquals(0, list.size());
	    assertTrue(list.isEmpty());

	    assertThrows(IndexOutOfBoundsException.class, () -> {
	        list.get(0);
	    });

	    assertEquals(-1, list.indexOf("apple"));
	}
	
    /**
     * Tests isEmpty() on a new and non-empty list.
     */

	@Test
	public void testIsEmpty() {
		SortedList<String> list = new SortedList<String>();
		
        // Tests new list being empty
        assertTrue(list.isEmpty());

        // After adding, it should not be empty
        list.add("apple");
        assertFalse(list.isEmpty());
	}
	
    /**
     * Tests whether E is in an empty list, has present/absent elements, and null.
     */
	@Test
	public void testContains() {
		SortedList<String> list = new SortedList<String>();
		
	    // Empty list and should be false 
	    assertFalse(list.contains("apple"));

	    // Add some elements
	    list.add("apple");
	    list.add("banana");
	    list.add("cherry");

	    assertTrue(list.contains("apple"));
	    assertTrue(list.contains("banana"));
	    assertTrue(list.contains("cherry"));

	    // Not element thats there should be false
	    assertFalse(list.contains("date"));

	    // contains(null)
	    assertFalse(list.contains(null));
	}
	
    /**
     * Tests equals() by comparing lists with same and different contents.
     */
	@Test
	public void testEquals() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
        // list1 and list2 the same
        list1.add("apple");
        list1.add("banana");
        list1.add("cherry");

        list2.add("apple");
        list2.add("banana");
        list2.add("cherry");

        // New list with different element 
        list3.add("apple");
        list3.add("banana");
        list3.add("date");

        assertEquals(list1, list2);
        assertEquals(list2, list1);

        assertNotEquals(list1, list3);
        assertNotEquals(list3, list1);

        assertNotEquals(null, list1);
        assertNotEquals("not a list", list1);
	}
	
    /**
     * Tests hashCode() for whether they hash the same or different
     */
	@Test
	public void testHashCode() {
		SortedList<String> list1 = new SortedList<String>();
		SortedList<String> list2 = new SortedList<String>();
		SortedList<String> list3 = new SortedList<String>();
		
        // list1 and list2 same
        list1.add("apple");
        list1.add("banana");
        list1.add("cherry");

        list2.add("apple");
        list2.add("banana");
        list2.add("cherry");

        // adding to list3 with one different element
        list3.add("apple");
        list3.add("banana");
        list3.add("date");

        // Same lists should have same hashCode
        assertEquals(list1.hashCode(), list2.hashCode());

        // Different lists should have different hashCode
        assertNotEquals(list1.hashCode(), list3.hashCode());
	}

}
 