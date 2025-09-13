package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Tests the CourseNameValidatorFSM class
 */
class CourseNameValidatorTest {
	/**
	 * Creates the CourseNameValidatorFSM variable "c" for testing
	 */
    private final CourseNameValidator validator = new CourseNameValidator();

	/**
	 * tests valid prefix 1 letter
	 */
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
        Exception e = assertThrows(InvalidTransitionException.class,
            () -> validator.isValid("116CSC"));
        assertEquals("Course name must start with a letter.", e.getMessage());
    }
	/**
	 * tests invalid nonalpha numeric
	 */
    @Test
    void testInvalidNonAlphaNumeric() {
        Exception e = assertThrows(InvalidTransitionException.class,
            () -> validator.isValid("CSC116"));
        assertEquals("Course name can only contain letters and digits.", e.getMessage());
    }
	/**
	 * tests invalid more than 4 letters
	 */
    @Test
    void testInvalidMoreThan4Letters() {
        Exception e = assertThrows(InvalidTransitionException.class,
            () -> validator.isValid("CSCSC116"));
        assertEquals("Course name cannot start with more than 4 letters.", e.getMessage());
    }
	/**
	 * tests invalid less than 3 digits
	 */
    @Test
    void testInvalidLessThan3Digits() {
        Exception e = assertThrows(InvalidTransitionException.class,
            () -> validator.isValid("CSC11H"));
        assertEquals("Course name must have 3 digits.", e.getMessage());
    }
	/**
	 * tests invalid more than 3 digits
	 */
    @Test
    void testInvalidMoreThan3Digits() {
        Exception e = assertThrows(InvalidTransitionException.class,
            () -> validator.isValid("CSC1161"));
        assertEquals("Course name can only have 3 digits.", e.getMessage());
    }
	/**
	 * tests invalid more than 1 suffix letter
	 */
    @Test
    void testInvalidMoreThan1SuffixLetter() {
        Exception e = assertThrows(InvalidTransitionException.class,
            () -> validator.isValid("CSC116AB"));
        assertEquals("Course name can only have a 1 letter suffix.", e.getMessage());
    }
	/**
	 * tests invalid digit after suffix
	 */
    @Test
    void testInvalidDigitAfterSuffix() {
        Exception e = assertThrows(InvalidTransitionException.class,
            () -> validator.isValid("CSC116A1"));
        assertEquals("Course name cannot contain digits after the suffix.", e.getMessage());
    }

	/**
	 * tests invalid empty string
	 */
    @Test
    void testInvalidEmptyString() {
        Exception e = assertThrows(InvalidTransitionException.class,
            () -> validator.isValid(" "));
        assertEquals("Course name can only contain letters and digits.", e.getMessage());
    }
	/**
	 * tests invalid InvalidOneDigitThenLetter
	 */
    @Test
    void testInvalidOnlyLetters() {
        Exception e = assertThrows(InvalidTransitionException.class,
            () -> validator.isValid("CSCCSC"));
        assertEquals("Course name cannot start with more than 4 letters.", e.getMessage());
    }
	/**
	 * tests invalid InvalidTwoDigitsThenLetter
	 */
    @Test
    void testInvalidTwoDigitsThenLetter() {
        Exception e = assertThrows(InvalidTransitionException.class,
            () -> validator.isValid("CSC11A"));
        assertEquals("Course name must have 3 digits.", e.getMessage());
    }
	/**
	 * tests invalid OneDigitThenLetter
	 */
    @Test
    void testInvalidOneDigitThenLetter() {
        Exception e = assertThrows(InvalidTransitionException.class,
            () -> validator.isValid("CSC1A"));
        assertEquals("Course name must have 3 digits.", e.getMessage());
    }
	/**
	 * tests invalid four letters one digit
	 */
    @Test
    void testInvalidFourLettersOneDigit() {
        Exception e = assertThrows(InvalidTransitionException.class,
            () -> validator.isValid("CS1SA"));
        assertEquals("Course name must have 3 digits.", e.getMessage());
    }
	/**
	 * tests invalid short name
	 */
    @Test
    void testInvalidShortName() throws InvalidTransitionException {
        assertFalse(validator.isValid("CS1"), 
            "A 2-letter prefix plus one digit is too short, so isValid should return false");
    }
}