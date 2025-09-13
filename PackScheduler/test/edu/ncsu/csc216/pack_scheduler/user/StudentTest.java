package edu.ncsu.csc216.pack_scheduler.user;


import static org.junit.jupiter.api.Assertions.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import org.junit.jupiter.api.Test;

/**
 * Tests the Student object.
 * @author SarahHeckman
 */
public class StudentTest {
	
	/** Test Student's first name. */
	private String firstName = "first";
	/** Test Student's last name */
	private String lastName = "last";
	/** Test Student's id */
	private String id = "flast";
	/** Test Student's email */
	private String email = "first_last@ncsu.edu";
	/** Test Student's hashed password */
	private String hashPW;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
    /** Test max credits */
    public static int maxCredits = 15;
	
	//This is a block of code that is executed when the StudentTest object is
	//created by JUnit.  Since we only need to generate the hashed version
	//of the plaintext password once, we want to create it as the StudentTest object is
	//constructed.  By automating the hash of the plaintext password, we are
	//not tied to a specific hash implementation.  We can change the algorithm
	//easily.
	{
		try {
			String plaintextPW = "password";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(plaintextPW.getBytes());
			this.hashPW = Base64.getEncoder().encodeToString(digest.digest());
		} catch (NoSuchAlgorithmException e) {
			fail("An unexpected NoSuchAlgorithmException was thrown.");
		}
	}
	
	/**
	 * Test toString() method.
	 */
	@Test
	public void testToString() {
		Student s1 = new Student(firstName, lastName, id, email, hashPW);
		assertEquals("first,last,flast,first_last@ncsu.edu," + hashPW + ",18", s1.toString());
	}


    /**
     * Tests the Student constructor with all fields
     */
    @Test
    void testStudentStringStringStringStringStringInt() {
        Student s = assertDoesNotThrow(
                () -> new Student(firstName, lastName, id, email, hashPW, maxCredits),
                "Should not throw exception");
        
        assertEquals(firstName, s.getFirstName());
        assertEquals(lastName, s.getLastName());
        assertEquals(id, s.getId());
        assertEquals(email, s.getEmail());
        assertEquals(hashPW, s.getPassword());
        assertEquals(maxCredits, s.getMaxCredits());
        
        Exception e1 = assertThrows(IllegalArgumentException.class,
                () -> new Student(null, lastName, id, email, hashPW, maxCredits));
        assertEquals("Invalid first name", e1.getMessage());
        
        e1 = assertThrows(IllegalArgumentException.class,
                () -> new Student("", lastName, id, email, hashPW, maxCredits));
        assertEquals("Invalid first name", e1.getMessage());
        
        Exception e2 = assertThrows(IllegalArgumentException.class,
                () -> new Student(firstName, null, id, email, hashPW, maxCredits));
        assertEquals("Invalid last name", e2.getMessage());
        
        e2 = assertThrows(IllegalArgumentException.class,
                () -> new Student(firstName, "", id, email, hashPW, maxCredits));
        assertEquals("Invalid last name", e2.getMessage());
        
        Exception e3 = assertThrows(IllegalArgumentException.class,
                () -> new Student(firstName, lastName, null, email, hashPW, maxCredits));
        assertEquals("Invalid id", e3.getMessage());
        
        e3 = assertThrows(IllegalArgumentException.class,
                () -> new Student(firstName, lastName, "", email, hashPW, maxCredits));
        assertEquals("Invalid id", e3.getMessage());
        
        Exception e4 = assertThrows(IllegalArgumentException.class,
                () -> new Student(firstName, lastName, id, null, hashPW, maxCredits));
        assertEquals("Invalid email", e4.getMessage());
        
        e4 = assertThrows(IllegalArgumentException.class,
                () -> new Student(firstName, lastName, id, "", hashPW, maxCredits));
        assertEquals("Invalid email", e4.getMessage());
        
        e4 = assertThrows(IllegalArgumentException.class,
                () -> new Student(firstName, lastName, id, "invalid.email", hashPW, maxCredits));
        assertEquals("Invalid email", e4.getMessage());
        
        e4 = assertThrows(IllegalArgumentException.class,
                () -> new Student(firstName, lastName, id, "invalid@email", hashPW, maxCredits));
        assertEquals("Invalid email", e4.getMessage());
        
        e4 = assertThrows(IllegalArgumentException.class,
                () -> new Student(firstName, lastName, id, "invalid.email@", hashPW, maxCredits));
        assertEquals("Invalid email", e4.getMessage());
        
        Exception e5 = assertThrows(IllegalArgumentException.class,
                () -> new Student(firstName, lastName, id, email, null, maxCredits));
        assertEquals("Invalid password", e5.getMessage());
        
        e5 = assertThrows(IllegalArgumentException.class,
                () -> new Student(firstName, lastName, id, email, "", maxCredits));
        assertEquals("Invalid password", e5.getMessage());
        
        Exception e6 = assertThrows(IllegalArgumentException.class,
                () -> new Student(firstName, lastName, id, email, hashPW, 2));
        assertEquals("Invalid max credits", e6.getMessage());
        
        e6 = assertThrows(IllegalArgumentException.class,
                () -> new Student(firstName, lastName, id, email, hashPW, 19));
        assertEquals("Invalid max credits", e6.getMessage());
    }

    /**
     * Tests the Student constructor with default max credits
     */
    @Test
    void testStudentStringStringStringStringString() {
        Student s = assertDoesNotThrow(
                () -> new Student(firstName, lastName, id, email, hashPW),
                "Should not throw exception");
        
        assertEquals(firstName, s.getFirstName());
        assertEquals(lastName, s.getLastName());
        assertEquals(id, s.getId());
        assertEquals(email, s.getEmail());
        assertEquals(hashPW, s.getPassword());
        assertEquals(Student.MAX_CREDITS, s.getMaxCredits());
    }

    /**
     * Tests setFirstName()
     */
    @Test
    void testSetFirstName() {
        Student s = new Student(firstName, lastName, id, email, hashPW);
        
        s.setFirstName("newfirst");
        assertEquals("newfirst", s.getFirstName());
        
        Exception e1 = assertThrows(IllegalArgumentException.class,
                () -> s.setFirstName(null));
        assertEquals("Invalid first name", e1.getMessage());
        assertEquals("newfirst", s.getFirstName());
        
        e1 = assertThrows(IllegalArgumentException.class,
                () -> s.setFirstName(""));
        assertEquals("Invalid first name", e1.getMessage());
        assertEquals("newfirst", s.getFirstName());
    }

    /**
     * Tests setLastName()
     */
    @Test
    void testSetLastName() {
        Student s = new Student(firstName, lastName, id, email, hashPW);
        
        s.setLastName("newlast");
        assertEquals("newlast", s.getLastName());
        
        Exception e1 = assertThrows(IllegalArgumentException.class,
                () -> s.setLastName(null));
        assertEquals("Invalid last name", e1.getMessage());
        assertEquals("newlast", s.getLastName());
        
        e1 = assertThrows(IllegalArgumentException.class,
                () -> s.setLastName(""));
        assertEquals("Invalid last name", e1.getMessage());
        assertEquals("newlast", s.getLastName());
    }

    /**
     * Tests setEmail()
     */
    @Test
    void testSetEmail() {
        Student s = new Student(firstName, lastName, id, email, hashPW);
        
        s.setEmail("new@email.com");
        assertEquals("new@email.com", s.getEmail());
        
        Exception e1 = assertThrows(IllegalArgumentException.class,
                () -> s.setEmail(null));
        assertEquals("Invalid email", e1.getMessage());
        assertEquals("new@email.com", s.getEmail());
        
        e1 = assertThrows(IllegalArgumentException.class,
                () -> s.setEmail(""));
        assertEquals("Invalid email", e1.getMessage());
        assertEquals("new@email.com", s.getEmail());
        
        e1 = assertThrows(IllegalArgumentException.class,
                () -> s.setEmail("invalid.email"));
        assertEquals("Invalid email", e1.getMessage());
        assertEquals("new@email.com", s.getEmail());
        
        e1 = assertThrows(IllegalArgumentException.class,
                () -> s.setEmail("invalid@email"));
        assertEquals("Invalid email", e1.getMessage());
        assertEquals("new@email.com", s.getEmail());
        
        e1 = assertThrows(IllegalArgumentException.class,
                () -> s.setEmail("invalid.email@"));
        assertEquals("Invalid email", e1.getMessage());
        assertEquals("new@email.com", s.getEmail());
        
    }

    /**
     * Tests setPassword()
     */
    @Test
    void testSetPassword() {
        Student s = new Student(firstName, lastName, id, email, hashPW);
        
        s.setPassword("newpassword");
        assertEquals("newpassword", s.getPassword());
        
        Exception e1 = assertThrows(IllegalArgumentException.class,
                () -> s.setPassword(null));
        assertEquals("Invalid password", e1.getMessage());
        assertEquals("newpassword", s.getPassword());
        
        e1 = assertThrows(IllegalArgumentException.class,
                () -> s.setPassword(""));
        assertEquals("Invalid password", e1.getMessage());
        assertEquals("newpassword", s.getPassword());
    }

    /**
     * Tests setMaxCredits()
     */
    @Test
    void testSetMaxCredits() {
        Student s = new Student(firstName, lastName, id, email, hashPW, maxCredits);
        
        s.setMaxCredits(10);
        assertEquals(10, s.getMaxCredits());
        
        s.setMaxCredits(3);
        assertEquals(3, s.getMaxCredits());
        
        s.setMaxCredits(18);
        assertEquals(18, s.getMaxCredits());
        
        Exception e1 = assertThrows(IllegalArgumentException.class,
                () -> s.setMaxCredits(2));
        assertEquals("Invalid max credits", e1.getMessage());
        assertEquals(18, s.getMaxCredits());
        
        e1 = assertThrows(IllegalArgumentException.class,
                () -> s.setMaxCredits(19));
        assertEquals("Invalid max credits", e1.getMessage());
        assertEquals(18, s.getMaxCredits());
    }

    /**
     * Tests hashCode()
     */
    @Test
    void testHashCode() {
        Student s1 = new Student(firstName, lastName, id, email, hashPW, maxCredits);
        Student s2 = new Student(firstName, lastName, id, email, hashPW, maxCredits);
        Student s3 = new Student("different", lastName, id, email, hashPW, maxCredits);
        Student s4 = new Student(firstName, "different", id, email, hashPW, maxCredits);
        Student s5 = new Student(firstName, lastName, "different", email, hashPW, maxCredits);
        Student s6 = new Student(firstName, lastName, id, "different@email.com", hashPW, maxCredits);
        Student s7 = new Student(firstName, lastName, id, email, "different", maxCredits);
        Student s8 = new Student(firstName, lastName, id, email, hashPW, 10);
        
        assertEquals(s1.hashCode(), s2.hashCode());
        assertNotEquals(s1.hashCode(), s3.hashCode());
        assertNotEquals(s1.hashCode(), s4.hashCode());
        assertNotEquals(s1.hashCode(), s5.hashCode());
        assertNotEquals(s1.hashCode(), s6.hashCode());
        assertNotEquals(s1.hashCode(), s7.hashCode());
        assertNotEquals(s1.hashCode(), s8.hashCode());
    }

    /**
     * Tests equals()
     */
    @Test
    void testEquals() {
        Student s1 = new Student(firstName, lastName, id, email, hashPW, maxCredits);
        Student s2 = new Student(firstName, lastName, id, email, hashPW, maxCredits);
        Student s3 = new Student("different", lastName, id, email, hashPW, maxCredits);
        Student s4 = new Student(firstName, "different", id, email, hashPW, maxCredits);
        Student s5 = new Student(firstName, lastName, "different", email, hashPW, maxCredits);
        Student s6 = new Student(firstName, lastName, id, "different@email.com", hashPW, maxCredits);
        Student s7 = new Student(firstName, lastName, id, email, "different", maxCredits);
        Student s8 = new Student(firstName, lastName, id, email, hashPW, 10);
        
        assertEquals(s1, s1);
        assertEquals(s1, s2);
        
        assertNotEquals(s1, null);
        assertNotEquals(s1, "Not a Student");
        assertNotEquals(s1, s3);
        assertNotEquals(s1, s4);
        assertNotEquals(s1, s5);
        assertNotEquals(s1, s6);
        assertNotEquals(s1, s7);
        assertNotEquals(s1, s8);
    }
    
    /**
     * Tests compareTo()
     */
    @Test
    public void testCompareTo() {
        Student s1 = new Student("John", "Doe", "jdoe", "jdoe@ncsu.edu", "password");
        Student s2 = new Student("john", "doe", "jdoe2", "jdoe2@ncsu.edu", "password");
        Student s3 = new Student("Alice", "Smith", "asmith", "asmith@ncsu.edu", "password");
        
        assertEquals(0, s1.compareTo(s1));
        
        assertTrue(s1.compareTo(s3) < 0);  
        assertTrue(s3.compareTo(s1) > 0);  
        
        assertTrue(s1.compareTo(s2) < 0);  
        assertTrue(s2.compareTo(s1) > 0);
        
        Student s4 = new Student("John", "Doe", "jdoe3", "jdoe3@ncsu.edu", "password");
        assertTrue(s1.compareTo(s4) < 0);  
        
        try {
            s1.compareTo(null);
            fail("Should throw NullPointerException");
        } catch (NullPointerException e) {
            // Expected 
        }
    }
}
