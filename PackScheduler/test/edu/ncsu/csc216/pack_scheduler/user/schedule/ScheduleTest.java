package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * Unit tests for Schedule class. 
 * @author Udita & Tanmayi
 */
class ScheduleTest {

    private Schedule schedule;
    private Course c1;
    private Course c2;

    /**
     * Sets up a new Schedule and example Course instances
     * before each test.
     */
    @BeforeEach
    void setUp() {
        schedule = new Schedule();
        // create two sample courses for testing
        c1 = new Course("CSC116", "Intro to Programming - Java", 
                        "001", 3, "jdyoung2", "MW", 910, 1100);
        c2 = new Course("CSC216", "Software Development",        
                        "001", 3, "sesmith5", "TH", 1330, 1445);
    }

    /**
     * Tests for method removeCourseFromSchedule(Course).
     * removing from an empty schedule returns false
     * removing null returns false
     * removing an existing course returns true and actually removes it
     * removing the same course again returns false
     * after removing all courses, the schedule is empty
     */
    @Test
    void testRemoveCourseFromSchedule() {
        // no courses yet, so removal should fail
        assertFalse(schedule.removeCourseFromSchedule(c1),
                   "Empty schedule should not remove anything");
        // null argument, still false
        assertFalse(schedule.removeCourseFromSchedule(null),
                   "Removing null should return false");

        // add two courses
        assertTrue(schedule.addCourseToSchedule(c1), "c1 should be added");
        assertTrue(schedule.addCourseToSchedule(c2), "c2 should be added");

        // remove the first course
        assertTrue(schedule.removeCourseFromSchedule(c1),
                   "Should remove c1 successfully");

        // only c2 should be left
        String[][] table = schedule.getScheduledCourses();
        assertEquals(1, table.length,
                     "After removing c1, exactly one course should remain");
        assertEquals("CSC216", table[0][0],
                     "Remaining course name should be CSC216");
        assertEquals("001", table[0][1],
                     "Remaining course section should be 001");

        // removing c1 again should fail
        assertFalse(schedule.removeCourseFromSchedule(c1),
                    "c1 was already removed, so this should return false");

        // remove c2 too
        assertTrue(schedule.removeCourseFromSchedule(c2),
                   "Should remove c2 successfully");
        // schedule should now be empty
        assertEquals(0, schedule.getScheduledCourses().length,
                     "Schedule should be empty after removing all courses");
    }

    /**
     * Tests method resetSchedule().
     * original title is "My Schedule"
     * original schedule is empty
     * after adding courses and changing title, reset clears both
     */
    @Test
    void testResetSchedule() {
        // new schedule, should lead to original title and empty list
        assertEquals("My Schedule", schedule.getTitle(),
                     "Default title should be My Schedule");
        assertEquals(0, schedule.getScheduledCourses().length,
                     "New schedule should start empty");

        // add two courses and change the title
        assertTrue(schedule.addCourseToSchedule(c1), "Should add first course");
        assertTrue(schedule.addCourseToSchedule(c2), "Should add second course");
        schedule.setTitle("Fall Schedule");
        assertEquals(2, schedule.getScheduledCourses().length,
                     "Schedule should have 2 courses before reset");
        assertEquals("Fall Schedule", schedule.getTitle(),
                     "Title should be updated to Fall Schedule");

        // perform reset
        schedule.resetSchedule();

        // after reset, empty courses and default title
        assertEquals(0, schedule.getScheduledCourses().length,
                     "After reset, schedule should be empty");
        assertEquals("My Schedule", schedule.getTitle(),
                     "After reset, title should be My Schedule");
    }

    /**
     * Tests method getScheduledCourses().
     * empty schedule returns zero-length array
     * after adding courses, 
     * returned table matches each course’s short display
     */
    @Test
    void testGetScheduledCourses() {
    	
        String[][] empty = schedule.getScheduledCourses();
        assertNotNull(empty, "getScheduledCourses() should not return null");
        assertEquals(0, empty.length, "Empty schedule yields zero rows");

        // add two courses
        assertTrue(schedule.addCourseToSchedule(c1), "Add c1");
        assertTrue(schedule.addCourseToSchedule(c2), "Add c2");

        // get array
        String[][] table = schedule.getScheduledCourses();
        assertEquals(2, table.length,
                     "After adding 2 courses, should have 2 rows");

        // compare each row to the course’s getShortDisplayArray()
        assertArrayEquals(c1.getShortDisplayArray(), table[0],
                          "Row 0 should match c1 display");
        assertArrayEquals(c2.getShortDisplayArray(), table[1],
                          "Row 1 should match c2 display");
    }

    /**
     * Tests method setTitle(String).
     * setting to a non null title changes the title
     * setting to null throws IllegalArgumentException 
     * and leaves title unchanged
     */
    @Test
    void testSetTitle() {
        // initial title
        assertEquals("My Schedule", schedule.getTitle(),
                     "Initial title should be My Schedule");
        // change to a valid new title
        schedule.setTitle("Spring 2025");
        assertEquals("Spring 2025", schedule.getTitle(),
                     "setTitle should update the title");
        // setting null should throw and not change existing title
        try {
            schedule.setTitle(null);
            fail("setTitle(null) should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // check that the message is exactly what is expected
            assertEquals("Title cannot be null.", e.getMessage(),
                         "Exception message must be exactly 'Title cannot be null.'");
        }
        // and the title should stay the same
        assertEquals("Spring 2025", schedule.getTitle(),
                     "Title should remain unchanged after exception");
    }

}
