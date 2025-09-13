package edu.ncsu.csc216.pack_scheduler.util;
import static org.junit.Assert.*;
import org.junit.Test;
/**
* Tests the ArrayList class
* @author tanmayi
* @author udita
*/
public class ArrayListTest {
   /**
    * Tests ArrayList constructor
    */
   @Test
   public void testArrayList() {
       ArrayList<String> list = new ArrayList<String>();
       assertEquals(0, list.size());
   }
   /**
    * Tests add() method
    */
   @Test
   public void testAdd() {
       ArrayList<String> list = new ArrayList<String>();
      
       list.add(0, "apple");
       assertEquals(1, list.size());
       assertEquals("apple", list.get(0));
      
       list.add(1, "banana");
       assertEquals(2, list.size());
       assertEquals("banana", list.get(1));
      
       list.add(0, "apricot");
       assertEquals(3, list.size());
       assertEquals("apricot", list.get(0));
      
       list.add(2, "blueberry");
       assertEquals(4, list.size());
       assertEquals("blueberry", list.get(2));
      
       for (int i = 4; i < 15; i++) {
           list.add(i, "fruit" + i);
       }
       assertEquals(15, list.size());
      
       assertThrows(NullPointerException.class, () -> list.add(0, null));
       assertThrows(IllegalArgumentException.class, () -> list.add(0, "apple"));
       assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "cherry"));
       assertThrows(IndexOutOfBoundsException.class, () -> list.add(100, "cherry"));
   }
   /**
    * Tests remove() method
    */
   @Test
   public void testRemove() {
       ArrayList<String> list = new ArrayList<String>();
       list.add(0, "apple");
       list.add(1, "banana");
       list.add(2, "cherry");
       list.add(3, "date");
      
       assertEquals("banana", list.remove(1));
       assertEquals(3, list.size());
       assertEquals("cherry", list.get(1));
      
       assertEquals("date", list.remove(2));
       assertEquals(2, list.size());
      
       assertEquals("apple", list.remove(0));
       assertEquals(1, list.size());
      
       assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
       assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
   }
   /**
    * Tests set() method
    */
   @Test
   public void testSet() {
       ArrayList<String> list = new ArrayList<String>();
       list.add(0, "apple");
       list.add(1, "banana");
      
       assertEquals("banana", list.set(1, "blueberry"));
       assertEquals("blueberry", list.get(1));
      
       assertThrows(NullPointerException.class, () -> list.set(0, null));
       assertThrows(IllegalArgumentException.class, () -> list.set(0, "blueberry"));
       assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "cherry"));
       assertThrows(IndexOutOfBoundsException.class, () -> list.set(2, "cherry"));
   }
   /**
    * Tests get() method
    */
   @Test
   public void testGet() {
       ArrayList<String> list = new ArrayList<String>();
       list.add(0, "apple");
       list.add(1, "banana");
      
       assertEquals("apple", list.get(0));
       assertEquals("banana", list.get(1));
      
       assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
       assertThrows(IndexOutOfBoundsException.class, () -> list.get(2));
   }
   /**
    * Tests size() method
    */
   @Test
   public void testSize() {
       ArrayList<String> list = new ArrayList<String>();
       assertEquals(0, list.size());
      
       list.add(0, "apple");
       assertEquals(1, list.size());
      
       list.add(1, "banana");
       assertEquals(2, list.size());
      
       list.remove(0);
       assertEquals(1, list.size());
   }
}
