package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * A class for the custom implementation of an array list that doesn't allow
 * null or duplicate elements. This class extends the AbstractList superclass.
 * 
 * @param <E> the data type of the elements handled in the array list.
 * 
 * @author Tanmayi 
 * @author Udita 
 */
public class ArrayList<E> extends AbstractList<E> {

    /** Initial capacity of the list */
    private static final int INIT_SIZE = 10;

    /** The underlying array */
    private E[] list;

    /** The number of elements in the list */
    private int size;

    /**
     * Constructs an empty list with the initial capacity.
     */
    @SuppressWarnings("unchecked")
    public ArrayList() {
        list = (E[]) new Object[INIT_SIZE];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return list[index];
    }

    @Override
    public void add(int index, E element) {
        if (element == null) {
            throw new NullPointerException("Cannot add null element");
        }
        
        // Check for duplicates
        for (int i = 0; i < size; i++) {
            if (element.equals(list[i])) {
                throw new IllegalArgumentException("Duplicate element");
            }
        }
        
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        
        // Grow array if needed
        if (size == list.length) {
            growArray();
        }
        
        // Shift elements
        for (int i = size; i > index; i--) {
            list[i] = list[i - 1];
        }
        
        list[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        
        E removed = list[index];
        
        // Shift elements
        for (int i = index; i < size - 1; i++) {
            list[i] = list[i + 1];
        }
        
        // Clear last element
        list[size - 1] = null;
        size--;
        
        return removed;
    }

    @Override
    public E set(int index, E element) {
        if (element == null) {
            throw new NullPointerException("Cannot set null element");
        }
        
        // Check for duplicates (excluding current index)
        for (int i = 0; i < size; i++) {
            if (i != index && element.equals(list[i])) {
                throw new IllegalArgumentException("Duplicate element");
            }
        }
        
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Invalid index");
        }
        
        E oldElement = list[index];
        list[index] = element;
        return oldElement;
    }

    /**
     * Doubles the capacity of the underlying array when needed
     */
    @SuppressWarnings("unchecked")
    private void growArray() {
        E[] newList = (E[]) new Object[list.length * 2];
        for (int i = 0; i < list.length; i++) {
            newList[i] = list[i];
        }
        list = newList;
    }
}
