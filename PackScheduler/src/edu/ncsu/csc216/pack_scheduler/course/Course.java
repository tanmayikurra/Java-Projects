/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * Represents a university course with info such as 
 * name, title, section,
 * credit hours, instructor ID, 
 * meeting days, and optionally start/end times.
 * 
 * @author Udita Pericharla
 */
public class Course extends Activity implements Comparable<Course> {
	
	
	/**
	 * Constructs a Course object with values for all fields.
	 * @param name is the name of Course
	 * @param title is the title of Course
	 * @param section is the section of Course
	 * @param credits is the credit hours for Course
	 * @param instructorId is the instructor's unity id
	 * @param meetingDays is the meeting days for Course as series of chars
	 * @param startTime is the start time for Course
	 * @param endTime is the end time for Course
	 */
	public Course(String name, String title, String section, int credits, String instructorId,
            String meetingDays, int startTime, int endTime) {
        super(title, meetingDays, startTime, endTime);
        setName(name);
        setSection(section);
        setCredits(credits);
        setInstructorId(instructorId);
	}

	/**
	 * Creates a Course with the given name, title, section, credits, instructorId, and meetingDays for 
	 * courses that are arranged.
	 * @param name is the name of Course
	 * @param title is the title of Course
	 * @param section is the section of Course
	 * @param credits is the credit hours for Course
	 * @param instructorId is the instructor's unity id
	 * @param meetingDays is the meeting days for Course as series of chars
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays) {
	    this(name, title, section, credits, instructorId, meetingDays, 0, 0);
	}

	/**
	 * Returns the Course's name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

    /**
     * Sets the Course's name.  Delegates format‐checking to CourseNameValidator,
     * then enforces min/max length, letter/count rules.
     * @param name the name to set
     * @throws IllegalArgumentException if the name parameter is invalid
     */
	private void setName(String name) {
	    if (name == null) {
	        throw new IllegalArgumentException("Invalid course name.");
	    }
	    try {
	        if (!nameValidator.isValid(name)) {
	            throw new IllegalArgumentException("Invalid course name.");
	        }
	    } catch (InvalidTransitionException e) {
	        throw new IllegalArgumentException("Invalid course name.");
	    }
	    this.name = name;
	}


	/**
	 * Returns the Course's section
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the Course's section, must be exactly 3 digits. 
	 * @param section the section to set
	 * @throws IllegalArgumentException if section is null, not length 3, or has 
	 * chars other than numbers. 
	 */
	public void setSection(String section) {
	    final int sectionLength = 3;
	    if (section == null || section.length() != sectionLength) {
	        throw new IllegalArgumentException("Invalid section.");
	    }
	    for (int i = 0; i < section.length(); i++) {
	        if (!Character.isDigit(section.charAt(i))) {
	            throw new IllegalArgumentException("Invalid section.");
	        }
	    }
	    this.section = section;
	}

	/**
	 * Returns the Course's credits
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the Course's credit hours.
	 *
	 * @param credits refers to how many credits are in a course
	 * @throws IllegalArgumentException if credits is less than 1 or greater than 5
	 */
	public void setCredits(int credits) {
	    final int minCredits = 1;
	    final int maxCredits = 5;
	    if (credits < minCredits || credits > maxCredits) {
	        throw new IllegalArgumentException("Invalid credits.");
	    }
	    this.credits = credits;
	}

	/**
	 * Returns the Course's instructorID
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets the Course's instructor ID, can't be null/empty string.
	 *
	 * @param instructorId is the instructor ID to set
	 * @throws IllegalArgumentException if instructorId is null/empty
	 */
	public void setInstructorId(String instructorId) {
	    if (instructorId == null || instructorId.length() == 0) {
	        throw new IllegalArgumentException("Invalid instructor id.");
	    }
	    this.instructorId = instructorId;
	}

	/**
	 * Returns string representation for Course
	 */
	@Override
	public String toString() {
	    if ("A".equals(getMeetingDays())) {
	        return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays();
	    }
	    return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getMeetingDays()
	           + "," + getStartTime() + "," + getEndTime();
	}
	
	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
    /** FSM validator for course names */
    private CourseNameValidator nameValidator = new CourseNameValidator();


	/**
	 * Generated a hash code for the Course object.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
		return result;
	}

	/**
	 * Compares this Course to another object to see if they're equal.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * Returns an array of fields for the Course.
	 */
	@Override
	public String[] getShortDisplayArray() {
	    return new String[] {
	        name,               
	        section,           
	        getTitle(),         
	        getMeetingString()  
	    };
	}

	/**
	 * Returns array for more details about Course.
	 */
	@Override
	public String[] getLongDisplayArray() {
	    return new String[] {
	        name,
	        section,
	        getTitle(),
	        Integer.toString(credits),
	        instructorId,
	        getMeetingString(),         
	        ""                           
	    };
	}

	/**
	 * Sets the meeting days and times for the Course.
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
	    if (meetingDays == null || meetingDays.isEmpty()) {
	        throw new IllegalArgumentException("Invalid meeting days and times.");
	    }
	    if ("A".equals(meetingDays)) {
	        if (startTime != 0 || endTime != 0) {
	            throw new IllegalArgumentException("Invalid meeting days and times.");
	        }
	    } else {
	        for (int i = 0; i < meetingDays.length(); i++) {
	            char current = meetingDays.charAt(i);
	            if ("MTWHF".indexOf(current) == -1) {
	                throw new IllegalArgumentException("Invalid meeting days and times.");
	            }
	            // Look for any duplicate of 'current' earlier in the string
	            for (int j = 0; j < i; j++) {
	                if (meetingDays.charAt(j) == current) {
	                    throw new IllegalArgumentException("Invalid meeting days and times.");
	                }
	            }
	        }
	    }
	    super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Checks whether the given Activity 
	 * is a duplicate of this Course.
	 */
	@Override
	public boolean isDuplicate(Activity activity) {
	    // If activity is null or not the same class, it's not a duplicate
	    if (activity == null || activity.getClass() != this.getClass()) {
	        return false;
	    }
	    Course other = (Course) activity;
	    // Two courses are duplicates if their names match exactly
	    return this.getName().equals(other.getName());
	}
	
    /**
     * Compare the first by course name and then by section.
     * @param other the other Course to compare to
     * @return negative if this < other, zero if equal, positive if this > other
     */
	@Override
	public int compareTo(Course other) {
	    if (other == null) {
	        throw new NullPointerException("Can't compare to null");
	    }
	    int nameComparison = this.name.compareTo(other.name);
	    
	    if (nameComparison != 0) {
	        return nameComparison;
	    }
	    return this.section.compareTo(other.section);
	}
	


}