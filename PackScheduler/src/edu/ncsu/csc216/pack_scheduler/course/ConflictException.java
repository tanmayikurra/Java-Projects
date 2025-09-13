package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Exception thrown when there is a conflict in two activities
 * meeting days and times.
 * 
 * @author Udita Pericharla
 */
public class ConflictException extends Exception {


    /** ID used for serialization. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a ConflictException with a custom message.
     * 
     * @param message the detail message
     */
    public ConflictException(String message) {
        super(message);
    }

    /**
     * Constructs a ConflictException with a default message.
     */
    public ConflictException() {
        this("Schedule conflict.");
    }
}
