package edu.ncsu.csc216.pack_scheduler.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
/**
 *  Class tests Registration Manager
 */
public class RegistrationManagerTest {
    /** The RegistrationManager instance under test. */
    private RegistrationManager manager;
    
    /** The registrar's username loaded from properties file. */
    private String registrarUsername;
    
    /** The registrar's password loaded from properties file. */
    private String registrarPassword;
    
    /** Path to the registrar properties file. */
    private static final String PROP_FILE = "registrar.properties";
    


    /**
     * Sets up the RegistrationManager and clears the data.
     * @throws Exception if error
     */
    @BeforeEach
    public void setUp() throws Exception {
        manager = RegistrationManager.getInstance();
        manager.clearData();
        manager.logout();
        
        Properties prop = new Properties();
        try (FileInputStream input = new FileInputStream(PROP_FILE)) {
            prop.load(input);
            registrarUsername = prop.getProperty("id");
            registrarPassword = prop.getProperty("pw");
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot process properties file.");
        }
    }
    
    /**
     * Tests the Get Course Catalog method.
     * Verifies that the course catalog is not null and can be accessed.
     */
    @Test
    public void testGetCourseCatalog() {
        CourseCatalog catalog = manager.getCourseCatalog();
        assertNotNull(catalog, "Course catalog should not be null");
        assertEquals(0, catalog.getCourseCatalog().length, "Course catalog should be empty initially");
        
        // Add a valid course that matches all naming rules:
        // - Total length between 5-8 characters
        // - Starts with 1-4 letters
        // - Followed by a space
        // - Ends with exactly 3 digits
        boolean added = catalog.addCourseToCatalog("CSC216", "Programming Concepts - Java", "001", 4, 
                "sesmith5", "MW", 900, 1030);
        assertTrue(added, "Course should be added successfully");
        
        assertEquals(1, catalog.getCourseCatalog().length, "Course catalog should have 1 course");
        
        String[][] courseArray = catalog.getCourseCatalog();
        assertEquals("CSC216", courseArray[0][0]);  // Verify the formatted name
        assertEquals("001", courseArray[0][1]);
        assertEquals("Programming Concepts - Java", courseArray[0][2]);
        assertEquals("MW 9:00AM-10:30AM", courseArray[0][3]);
    }

    /**
     * Tests the GetStudentDirectory method.
     * Verifies that the student directory is initially empty and can be populated.
     */
    @Test
    public void testGetStudentDirectory() {
        StudentDirectory directory = manager.getStudentDirectory();
        assertNotNull(directory, "Student directory should not be null");
        assertEquals(0, directory.getStudentDirectory().length, "Student directory should be empty initially");
        
        directory.addStudent("John", "Doe", "jdoe", "jdoe@ncsu.edu", "password", "password", 15);
        
        assertEquals(1, directory.getStudentDirectory().length, "Student directory should have 1 student");

        String[] student = directory.getStudentDirectory()[0];

        assertEquals("John", student[0]);
        assertEquals("Doe",  student[1]);
     	assertEquals("jdoe", student[2]);
    }


    /**
     * Tests the Login method.
     * Verifies successful login for students and registrar, and rejects invalid credentials.
     */
    @Test
    public void testLogin() {
        // -- nothing logged in at the start
        assertNull(manager.getCurrentUser(), "No one should be logged in yet.");

        // -- add & log in a student
        StudentDirectory dir = manager.getStudentDirectory();
        dir.addStudent("Alex", "Tanton", "atanton", "atanton@ncsu.edu", "pass123", "pass123", 12);
        assertTrue(manager.login("atanton", "pass123"), "Should log in student with correct creds.");
        assertEquals("atanton", manager.getCurrentUser().getId());

        manager.logout();

        // -- registrar login
        assertTrue(manager.login(registrarUsername, registrarPassword), "Registrar should log in.");
        assertEquals(registrarUsername, manager.getCurrentUser().getId());

        manager.logout();

        // -- non-existent user MUST throw IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
                     () -> manager.login("doesnotexist", "nopass"),
                     "Should throw if the ID is not found");

        // -- wrong‐password but valid ID just returns false
        assertFalse(manager.login("atanton", "wrongpass"),
                    "Should return false for a bad student password");
        assertFalse(manager.login(registrarUsername, "wrongpass"),
                    "Should return false for a bad registrar password");
    }


    /**
     * Tests the Logout method.
     * Verifies that the current user is cleared after logout.
     */  
    @Test
    public void testLogout() {
        // Add a student and login
        StudentDirectory dir = manager.getStudentDirectory();
        dir.addStudent("Alex", "Tanton", "atanton", "atanton@ncsu.edu", "pass123", "pass123", 12);
        manager.login("atanton", "pass123");

        // Make sure user is logged in
        assertNotNull(manager.getCurrentUser(), "Someone should be logged in.");

        // Call logout
        manager.logout();

        // Make sure user is now logged out
        assertNull(manager.getCurrentUser(), "After logout, no one should be logged in.");
    }
    
    /**
     * Tests the GetCurrentUser method.
     * Verifies that the correct user object is returned when logged in, and null otherwise.
     */
    @Test
    public void testGetCurrentUser() {
        // No one logged in yet
        assertNull(manager.getCurrentUser(), "Should be null at the beginning.");

        // Login a student
        StudentDirectory dir = manager.getStudentDirectory();
        dir.addStudent("Alex", "Tanton", "atanton", "atanton@ncsu.edu", "pass123", "pass123", 12);
        manager.login("atanton", "pass123");

        // Should now return the student
        assertNotNull(manager.getCurrentUser(), "Current user should not be null after login.");
        assertEquals("atanton", manager.getCurrentUser().getId(), "Should be Alex Tanton.");
    }

}