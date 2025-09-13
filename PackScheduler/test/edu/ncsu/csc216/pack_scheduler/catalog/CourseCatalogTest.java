package edu.ncsu.csc216.pack_scheduler.catalog;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for CourseCatalog using code provided by 216/217 
 * instructors and overall code from WolfScheduler and StudentDirectoryTest.
 * 
 * @author Udita Pericharla
 */
public class CourseCatalogTest {

    /** Path to a test file with courses */
    private final String validTestFile = "test-files/course_records.txt";
    /** Starting version of the course file */
    private final String starterTestFile = "test-files/starter_course_records.txt";

    /**
     * After each test, reset the test file.
     * @throws AssertionError if the file cant be reset properly
     */
    @Before
    public void setUp() {
        try {
            Scanner input = new Scanner(new File(starterTestFile));
            PrintWriter output = new PrintWriter(new File(validTestFile));
            while (input.hasNextLine()) {
                output.println(input.nextLine());
            }
            input.close();
            output.close();
        } catch (IOException e) {
            fail("Could not reset files.");
        }
    }

    /**
     * Test that a new catalog starts empty.
     * @throws AssertionError if the catalog isnt empty
     */
    @Test
    public void testNewCourseCatalog() {
        CourseCatalog catalog = new CourseCatalog();
        assertEquals(0, catalog.getCourseCatalog().length);
        catalog.newCourseCatalog();
        assertEquals(0, catalog.getCourseCatalog().length);
    }

    /**
     * Test loading courses from a file.
     * @throws AssertionError if the number of courses is incorrect
     */
    @Test
    public void testLoadCoursesFromFile() {
        CourseCatalog catalog = new CourseCatalog();
        catalog.loadCoursesFromFile(validTestFile);
        assertEquals(13, catalog.getCourseCatalog().length);
    }

    /**
     * Test adding a new course.
     * @throws AssertionError if course is not added or duplicate is allowed
     */
    @Test
    public void testAddCourseToCatalog() {
        CourseCatalog catalog = new CourseCatalog();
        assertTrue(catalog.addCourseToCatalog("CSC216", "Software Dev", "004", 3, "jdoe", "MW", 900, 1000));
        assertEquals(1, catalog.getCourseCatalog().length);
        assertFalse(catalog.addCourseToCatalog("CSC216", "Software Dev", "004", 3, "jdoe", "MW", 900, 1000));
    }

    /**
     * Test removing a course.
     * @throws AssertionError if the course is not removed correctly
     */
    @Test
    public void testRemoveCourseFromCatalog() {
        CourseCatalog catalog = new CourseCatalog();
        catalog.addCourseToCatalog("CSC216", "Software Dev", "004", 3, "jdoe", "MW", 900, 1000);
        assertTrue(catalog.removeCourseFromCatalog("CSC216", "004"));
        assertFalse(catalog.removeCourseFromCatalog("CSC216", "999"));
    }

    /**
     * Test getting a course.
     * @throws AssertionError if getting the course fails or wrong course is returned
     */
    @Test
    public void testGetCourseFromCatalog() {
        CourseCatalog catalog = new CourseCatalog();
        catalog.addCourseToCatalog("CSC216", "Software Dev", "004", 3, "jdoe", "MW", 900, 1000);
        assertNotNull(catalog.getCourseFromCatalog("CSC216", "004"));
        assertNull(catalog.getCourseFromCatalog("CSC226", "004"));
    }

    /**
     * Test saving the course catalog to a file.
     * @throws AssertionError if the file isnt saved right
     */
    @Test
    public void testSaveCourseCatalog() {
        CourseCatalog catalog = new CourseCatalog();
        catalog.addCourseToCatalog("CSC216", "Software Dev", "004", 3, "jdoe", "MW", 900, 1000);
        String saveFile = "test-files/actual_course_records.txt";
        catalog.saveCourseCatalog(saveFile);

        File f = new File(saveFile);
        assertTrue(f.exists());
    }
}
