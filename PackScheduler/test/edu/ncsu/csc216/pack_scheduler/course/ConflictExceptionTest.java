package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests for the ConflictException class.
 */
class ConflictExceptionTest {

    /**
     * Test method for {@link ConflictException#ConflictException(String)}.
     */
    @Test
    void testConflictExceptionString() {
        ConflictException ce = new ConflictException("Custom exception message");
        assertEquals("Custom exception message", ce.getMessage());
    }

    /**
     * Test method for {@link ConflictException#ConflictException()}.
     */
    @Test
    void testConflictExceptionDefault() {
        ConflictException ce = new ConflictException();
        assertEquals("Schedule conflict.", ce.getMessage());
    }
}
