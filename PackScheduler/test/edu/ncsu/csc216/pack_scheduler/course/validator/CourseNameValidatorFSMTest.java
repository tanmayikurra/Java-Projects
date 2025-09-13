package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests the CourseNameValidatorFSM class
 * @author tanmayi and udita
 */
class CourseNameValidatorFSMTest {
	/**
	 * initializes new validator for comparisons
	 */
    private final CourseNameValidatorFSM validator = new CourseNameValidatorFSM();

    // Test valid course names
    @Test
    void testValid1LetterPrefix() {
        assertDoesNotThrow(() -> assertTrue(validator.isValid("C116")));
    }
	/**
	 * tests valid prefix 
	 */
    @Test
    void testValid2LetterPrefix() {
        assertDoesNotThrow(() -> assertTrue(validator.isValid("CS116")));
    }
	/**
	 * tests valid prefix 
	 */
    @Test
    void testValid3LetterPrefix() {
        assertDoesNotThrow(() -> assertTrue(validator.isValid("CSC116")));
    }
	/**
	 * tests valid prefix 
	 */
    @Test
    void testValid4LetterPrefix() {
        assertDoesNotThrow(() -> assertTrue(validator.isValid("CSCA116")));
    }
	/**
	 * tests valid prefix with suffix
	 */
    @Test
    void testValid1LetterPrefixWithSuffix() {
        assertDoesNotThrow(() -> assertTrue(validator.isValid("C116A")));
    }
	/**
	 * tests valid prefix with suffix
	 */
    @Test
    void testValid2LetterPrefixWithSuffix() {
        assertDoesNotThrow(() -> assertTrue(validator.isValid("CS116A")));
    }
	/**
	 * tests valid prefix with suffix
	 */
    @Test
    void testValid3LetterPrefixWithSuffix() {
        assertDoesNotThrow(() -> assertTrue(validator.isValid("CSC116A")));
    }
	/**
	 * tests valid prefix with suffix
	 */
    @Test
    void testValid4LetterPrefixWithSuffix() {
        assertDoesNotThrow(() -> assertTrue(validator.isValid("CSCA116A")));
    }
    /**
     * tests invalid starts with digit
     */
    @Test
    void testInvalidStartWithDigit() {
        try {
            validator.isValid("1CSC116");
            fail("Expected an InvalidTransitionException");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name must start with a letter.", e.getMessage());
        }
    }

    /**
     * tests invalid nonalpha numeric
     */
    @Test
    void testInvalidNonAlphanumeric() {
        try {
            validator.isValid("CSC 116");
            fail("Expected an InvalidTransitionException");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name can only contain letters and digits.", e.getMessage());
        }
    }


    /**
     * tests invalid more than 4 letters
     */
    @Test
    void testInvalidMoreThan4Letters() {
        try {
            validator.isValid("CSCSC116");
            fail("Expected an InvalidTransitionException");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name cannot start with more than 4 letters.", 
                         e.getMessage());
        }
    }

    /**
     * tests invalid less than 3 digits
     */
    @Test
    void testInvalidLessThan3Digits() {
        try {
            validator.isValid("CSC11A");
            fail("Expected an InvalidTransitionException");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name must have 3 digits.", e.getMessage());
        }
    }

    /**
     * tests invalid more than 3 digits
     */
    @Test
    void testInvalidMoreThan3Digits() {
        try {
            validator.isValid("CSC1167");
            fail("Expected an InvalidTransitionException");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name can only have 3 digits.", e.getMessage());
        }
    }

    /**
     * tests invalid more than 1 suffix letter
     */
    @Test
    void testInvalidMoreThan1SuffixLetter() {
        try {
            validator.isValid("CSC116AB");
            fail("Expected an InvalidTransitionException");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name can only have a 1 letter suffix.", e.getMessage());
        }
    }

    /**
     * tests invalid digit after suffix
     */
    @Test
    void testInvalidDigitAfterSuffix() {
        try {
            validator.isValid("CSC116A1");
            fail("Expected an InvalidTransitionException");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name cannot contain digits after the suffix.", 
                         e.getMessage());
        }
    }

    /**
     * tests invalid only letters (no digits at all)
     */
    @Test
    void testInvalidOnlyLetters() {
        try {
            validator.isValid("CSCCSC");
            fail("Expected an InvalidTransitionException");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name cannot start with more than 4 letters.", e.getMessage());
        }
    }

    /**
     * tests invalid two digits then letter (too few digits)
     */
    @Test
    void testInvalidTwoDigitsThenLetter() {
        try {
            validator.isValid("CSC11A");
            fail("Expected an InvalidTransitionException");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name must have 3 digits.", e.getMessage());
        }
    }

    /**
     * tests invalid one digit then letter (too few digits)
     */
    @Test
    void testInvalidOneDigitThenLetter() {
        try {
            validator.isValid("CSC1A");
            fail("Expected an InvalidTransitionException");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name must have 3 digits.", e.getMessage());
        }
    }

    /**
     * tests invalid four letters one digit (prefix + digit only)
     */
    @Test
    void testInvalidFourLettersOneDigit() {
        try {
            validator.isValid("CS1SA");
            fail("Expected an InvalidTransitionException");
        } catch (InvalidTransitionException e) {
            assertEquals("Course name must have 3 digits.", e.getMessage());
        }
    }

    /**
     * tests invalid short name (too short to even reach digits state)
     */
    @Test
    void testInvalidShortName() {
        try {
            boolean res = validator.isValid("CS1");
            assertFalse(res, "A 2-letter prefix plus one digit is too short, so isValid should return false");
        } catch (InvalidTransitionException e) {
            fail("Did not expect an exception for very short name should return false");
        }
    }
}
