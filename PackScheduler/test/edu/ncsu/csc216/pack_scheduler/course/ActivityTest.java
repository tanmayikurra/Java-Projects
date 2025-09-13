package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests the checkConflict() method in the Activity class.
 * 
 * @author Udita Pericharla
 */
public class ActivityTest {

    /**
     * Test that two activities on 
     * different days with the same time do not conflict.
     */
    @Test
    public void testNoConflictDifferentDays() {
        Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", "MW", 1330, 1445);
        Activity a2 = new Course("CSC216", "Software Development Fundamentals", "002", 3, "sesmith5", "TH", 1330, 1445);

        assertDoesNotThrow(() -> a1.checkConflict(a2));
    }

    /**
     * Test that two activities on the
     * same day with overlapping times do conflict.
     */
    @Test
    public void testConflictOverlapSameDay() {
        Activity a1 = new Course("CSC116", "Intro to Programming", "002", 3, "jdoe", "M", 1300, 1430);
        Activity a2 = new Course("CSC 226", "Discrete Math", "001", 3, "asmith", "M", 1400, 1530);

        assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
    }

    /**
     * Test that an arranged activity does
     * not conflict with a scheduled activity.
     */
    @Test
    public void testNoConflictArranged() {
        Activity a1 = new Course("CSC 499", "Independent Study", "001", 3, "prof", "A");
        
        Activity a2 = new Course("CSC 230", "C and Tools", "001", 3, "prof2", "MW", 1000, 1115);

        assertDoesNotThrow(() -> a1.checkConflict(a2));
        assertDoesNotThrow(() -> a2.checkConflict(a1));
    }
    
    /**
     * Tests that two activities with 
     * the same meeting times but
     * on different days
     * dont conflict.
     */
    @Test
    public void testCheckConflictNoConflict() {
        Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", "MW", 1330, 1445);
        Activity a2 = new Course("CSC 226", "Discrete Math", "001", 3, "tmbarnes", "TH", 1330, 1445);
        
        // These have same times, but different days, so there should be no conflict
        assertDoesNotThrow(() -> a1.checkConflict(a2));
        assertDoesNotThrow(() -> a2.checkConflict(a1));
    }
    /**
    /**
     * Tests that there are two identical activities
     * on the same day.
     */
    @Test
    public void testCheckConflictWithConflict() {
        Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", "MW", 1330, 1445);
        Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", "M", 1330, 1445);
    	
        Exception e1 = assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
        assertEquals("Schedule conflict.", e1.getMessage());
    	
        Exception e2 = assertThrows(ConflictException.class, () -> a2.checkConflict(a1));
        assertEquals("Schedule conflict.", e2.getMessage());
    }
    /**
     * Tests a conflict happening when two activities 
     * share one meeting day overlapping
     */
     @Test
     public void testConflictOneOverlappingDayOnly() {
    	    Activity a1 = new Course("CSC216", "Software Dev", "001", 3, "sesmith5", "MW", 1330, 1445);
    	    Activity a2 = new Course("CSC 230", "C and Tools", "001", 3, "jdoe", "WTH", 1400, 1500);

    	    Exception e = assertThrows(ConflictException.class, () -> a1.checkConflict(a2));
    	    assertEquals("Schedule conflict.", e.getMessage());
    	}
}
