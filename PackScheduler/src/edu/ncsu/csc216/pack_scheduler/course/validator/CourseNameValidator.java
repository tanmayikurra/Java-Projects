package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 *Course Validator class that checks name is in correct
 *format. 
 *@author tanmayi and udita
 */
public class CourseNameValidator {

    /**
     *  Main State everything is based around
     */
    private State currentState;
    

    /**
     * lettercount for validation
     */
    private int letterCount;
    
    /**
     * digitcount for validation
     */
    private int digitCount;
    
    /**
     * initizializing initial state.
     */
    private final State initialState = new InitialState();
    
    /**
     * initializing letterState
     */
    private final State letterState = new LetterState();
    
    /* initizializing digitstate*/ 
    /**
     * Creates a new validator in the initial state.
     */
    private final State digitState = new DigitState();
    
    /**
     * initializing suffixstatee
     */
    private final State suffixState = new SuffixState();

    /**
     * Creates a new validator in the initial state.
     */
    public CourseNameValidator() {
        reset();
    }

    /**
     * Validates the given course name against the required format.
     * @param courseName the name to validate
     * @return true if the name is valid
     * @throws InvalidTransitionException if the name violates format rules
     */
    public boolean isValid(String courseName) throws InvalidTransitionException {
        if (courseName == null || courseName.isEmpty()) {
            throw new InvalidTransitionException("Course name cannot be empty");
        }
        reset();
        
        for (char c : courseName.toCharArray()) {
            processCharacter(c);
        }
        return (currentState == digitState && digitCount == DigitState.REQDIGS)
            || (currentState == suffixState);
    }


    /**
     * Helper method resets the validator to initial state.
     */
    private void reset() {
        currentState = initialState;
        letterCount = 0;
        digitCount = 0;
    }

    /**
     * Processes a single character 
     * @param c the character to process
     * @throws InvalidTransitionException if character causes invalid transition
     */
    private void processCharacter(char c) throws InvalidTransitionException {
        if (Character.isLetter(c)) {
            currentState.onLetter();
        } else if (Character.isDigit(c)) {
            currentState.onDigit();
        } else {
            currentState.onOther();
        }
    }

    /**
     * Abstract base class for all validator states.
     */
    private abstract class State {
        /**
         * Handles letter input for this state.
         * @throws InvalidTransitionException if letter is invalid in this state
         */
        public abstract void onLetter() throws InvalidTransitionException;

        /**
         * Handles digit input for this state.
         * @throws InvalidTransitionException if digit is invalid in this state
         */
        public abstract void onDigit() throws InvalidTransitionException;

        /**
         * Handles invalid input.
         * @throws InvalidTransitionException always throws for invalid characters
         */
        public void onOther() throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name can only contain letters and digits.");
        }
    }

    /**
     * Initial state before processing any characters.
     */
    private class InitialState extends State {
        @Override
        public void onLetter() {
            letterCount++;
            currentState = letterState;
        }

        @Override
        public void onDigit() throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name must start with a letter.");
        }
    }

    /**
     * State for processing prefix letters (1 to 4 letters).
     */
    private class LetterState extends State {
    	/**
    	 * max amount of prefixletters
    	 */
        private static final int MAXPREFLETS = 4;

        @Override
        public void onLetter() throws InvalidTransitionException {
            if (++letterCount > MAXPREFLETS) {
                throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
            }
        }

        @Override
        public void onDigit() {
            digitCount++;
            currentState = digitState;
        }
    }

    /**
     * State for processing course number digits (exactly 3 digits).
     */
    private class DigitState extends State {
    	/**
    	 * required digits
    	 */
        private static final int REQDIGS = 3;

        @Override
        public void onLetter() throws InvalidTransitionException {
            if (digitCount < REQDIGS) {
                throw new InvalidTransitionException("Course name must have 3 digits.");
            }
            currentState = suffixState;
        }

        @Override
        public void onDigit() throws InvalidTransitionException {
            if (++digitCount > REQDIGS) {
                throw new InvalidTransitionException("Course name can only have 3 digits.");
            }
        }
    }

    /**
     * State for processing optional single letter suffix.
     */
    private class SuffixState extends State {
        @Override
        public void onLetter() throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
        }

        @Override
        public void onDigit() throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");
        }
    }
}
