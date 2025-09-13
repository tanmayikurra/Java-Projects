package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * This class represents a student that has
 * details specific to them and an enrollment cap.
 * A student has a first name, last name, ID, email, hashed password,
 * and maximum number of credits they can enroll in.
 * 
 * @author Udita Pericharla
 */
public class Student extends User implements Comparable<Student> {
    
    /** The max credits ANY student can have */
    public static final int MAX_CREDITS = 18;

    /** Max credits this student can enroll in */
    private int maxCredits;
    
    /** This student’s schedule of courses */
    private Schedule schedule; 

    /**
     * Constructs a student with all fields including maxCredits.
     * 
     * @param firstName is the first name of the student
     * @param lastName is the last name of the student
     * @param id is the student ID
     * @param email is the student email address
     * @param password is a hashed password that the student chose
     * @param maxCredits is max credits credit can take
     */
    public Student(String firstName, String lastName, String id, String email, String password, int maxCredits) {
    	super(firstName, lastName, id, email, password);
        setMaxCredits(maxCredits);
        this.schedule = new Schedule();
    }

    /**
     * Constructs a student with default maxCredits of 18.
     * 
     * @param firstName is the first name of the student
     * @param lastName is the last name of the student
     * @param id is the student ID
     * @param email is the student email address
     * @param password is a hashed password that the student chose
     */
    public Student(String firstName, String lastName, String id, String email, String password) {
        this(firstName, lastName, id, email, password, MAX_CREDITS);
    }

    /**
     * Returns the maximum number of credits the student can take.
     * 
     * @return the max credits
     */
    public int getMaxCredits() {
        return maxCredits;
    }
    
    /**
     * Returns the schedule associated with this student.
     *
     * @return the Schedule object for this student
     */
    public Schedule getSchedule() {
        return schedule;
    }

    // makes sure to contain all elements needed for it to be valid

    /**
     * Sets the maximum number of credits the student can take.
     * 
     * @param maxCredits the max credits to set
     * @throws IllegalArgumentException if maxCredits is less than 3 or greater than 18
     */
    public void setMaxCredits(int maxCredits) {
        if (maxCredits < 3 || maxCredits > MAX_CREDITS) {
            throw new IllegalArgumentException("Invalid max credits");
        }
        this.maxCredits = maxCredits;
    }



	@Override
	public String toString() {
	    return firstName + "," 
	         + lastName  + "," 
	         + id        + "," 
	         + email     + "," 
	         + password  + "," 
	         + maxCredits;
	}

    /**
     * Shows formatted order by lastName, then firstName, then id.
     * @param s the other Student to compare against
     * @return negative if this < s and zero if equal and positive if this > s
     */
    @Override
    public int compareTo(Student s) {
        if (s == null) {
            throw new NullPointerException("Cannot compare to null");
        }
        // Compare by last name
        int lastNameCmp = this.lastName.compareTo(s.lastName);
        if (lastNameCmp != 0) {
            return lastNameCmp;
        }

        int firstNameCmp = this.firstName.compareTo(s.firstName);
        if (firstNameCmp != 0) {
            return firstNameCmp;
        }

        return this.id.compareTo(s.id);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCredits;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
	    if (this == obj) {
	        return true;
	    }
	    if (!super.equals(obj)) {
	        return false;
	    }
	    if (getClass() != obj.getClass()) {
	        return false;
	    }
	    Student other = (Student) obj;
	    return this.maxCredits == other.maxCredits;
	}
}