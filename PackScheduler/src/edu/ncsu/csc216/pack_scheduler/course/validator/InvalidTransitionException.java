/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Checked Exception InvalidTransitionException
 * @author Udita and Tanmayi
 */
public class InvalidTransitionException extends Exception {
	
	/**
	 * serialVersionUID/Checked exception 
	 * thrown when an FSM transition not allowed.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with required/specific message
	 */
    public InvalidTransitionException() {
        super("Invalid FSM Transition.");
    }
	
	/**
	 * Constructor with custom message
	 * @param message is the message to use
	 */
    public InvalidTransitionException(String message) {
        super(message);
    }
}
