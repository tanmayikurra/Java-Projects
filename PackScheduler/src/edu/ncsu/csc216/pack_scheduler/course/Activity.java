package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Activities that represents a bunch of activities such as 
 * meeting days, start time, and end time.
 * 
 * Got help from Amory on 5/29 during office hours
 * to fix my tests that were failing. We found simple errors within my source code 
 * which we ended up being able to work through and fix together. 
 */
public abstract class Activity implements Conflict {

	/** Course's title. */
	private String title;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's starting time */
	private int startTime;
	/** Course's ending time */
	private int endTime;
	
	/**
	 * Constructs an Activity object
	 * with title, days, end and start time. 
	 * @param title the title of the activity
	 * @param meetingDays the days student meets 
	 * @param startTime the start time 
	 * @param endTime the end time 
	 * @throws IllegalArgumentException if any parameter is invalid
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
	    super();
	    setTitle(title);
	    setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Returns the Course's title
	 * 
	 * @return the title
	 */
	public String getTitle() {
	    return title;
	}

	/**
	 * Sets the Course's title
	 * 
	 * @param title the title to set
	 */
	public void setTitle(String title) {
	    if (title == null || "".equals(title)) {
	        throw new IllegalArgumentException("Invalid title.");
	    }
	
	    this.title = title;
	}

	/**
	 * Returns the Course's meeting days
	 * 
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
	    return meetingDays;
	}

	/**
	 * Returns the Course's start time
	 * 
	 * @return the startTime
	 */
	public int getStartTime() {
	    return startTime;
	}

	/**
	 * Returns the Course's end time
	 * 
	 * @return the endTime
	 */
	public int getEndTime() {
	    return endTime;
	}

	/**
	 * Sets the Course's meeting days
	 * 
	 * @param meetingDays the meetingDays to set
	 * @param startTime is the start time of course
	 * @param endTime is the end time of course
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
	    int startHour = startTime / 100;
	    int startMin = startTime % 100;
	    int endHour = endTime / 100;
	    int endMin = endTime % 100;

	    if (startHour < 0 || startHour >= 24 || startMin < 0 || startMin >= 60 ||
	        endHour < 0 || endHour >= 24 || endMin < 0 || endMin >= 60 || endTime < startTime) {
	        throw new IllegalArgumentException("Invalid meeting days and times.");
	    }

	    this.meetingDays = meetingDays;
	    this.startTime = startTime;
	    this.endTime = endTime;
	}

	/**
	 * Returns a string representation of 
	 * the Course's meeting days and times.
	 * 
	 * @return Course's meeting days and times.
	 */
	public String getMeetingString() {
	    if ("A".equals(meetingDays)) {
	        return "Arranged";
	    }
	
	    return meetingDays + " " + getTimeString(startTime) + "-" + getTimeString(endTime);
	}
	/**
	 * Returns a short display array 
	 * having key info for an Activity.
	 *
	 * @return a short String array of Activity details
	 */
	public abstract String[] getShortDisplayArray();

	/**
	 * Returns a long display array having full info for an Activity.
	 *
	 * @return a long String array of Activity details
	 */
	public abstract String[] getLongDisplayArray();


	/**
	 * Returns the time in AM/PM format.
	 * 
	 * @param time as an integer
	 * @return time as a string
	 */
	private String getTimeString(int time) {
	    int hour = time / 100;
	    int min = time % 100;
	    boolean morning = true;
	
	    if (hour >= 12) {
	        hour -= 12;
	        morning = false;
	    }
	    if (hour == 0) {
	        hour = 12;
	    }
	
	    String mins = "" + min;
	    if (min < 10) {
	        mins = "0" + mins;
	    }
	
	    String end = morning ? "AM" : "PM";
	
	    return hour + ":" + mins + end;
	}

	/**
	 * Generates a hash code for the Activity 
	 * object based on its title, meeting days,
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	/**
	 * Compares this Activity to another object for equality.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	/**
	 * Checks if the given Activity is a duplicate of this one.
	 * @param activity the Activity to compare
	 * @return true if duplicate, false otherwise
	 */
	public abstract boolean isDuplicate(Activity activity);

    /**
     * Checks for a scheduling conflict with another Activity.
     * A conflict exists when there is two activities with sharing at 
     * least one meeting day with each other and 
     * their meeting times overlap with one another my at least a minute.
     * 
     * @param possibleConflictingActivity the Activity to compare to
     * @throws ConflictException if there is a conflict in meeting days and times
     */
    @Override
    public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
        // If either activity is "Arranged", they do not conflict
        if ("A".equals(this.getMeetingDays()) || "A".equals(possibleConflictingActivity.getMeetingDays())) {
            return;
        }

        // Loop through meeting days in this activity
        for (int i = 0; i < this.getMeetingDays().length(); i++) {
            char thisDay = this.getMeetingDays().charAt(i);

            // Check if the other activity meets on the same day
            if (possibleConflictingActivity.getMeetingDays().indexOf(thisDay) != -1) {
                int thisStart = this.getStartTime();
                int thisEnd = this.getEndTime();
                int otherStart = possibleConflictingActivity.getStartTime();
                int otherEnd = possibleConflictingActivity.getEndTime();

                // If there's any overlapping minute (shared minute included)
                if (thisStart < otherEnd && otherStart < thisEnd) {
                    throw new ConflictException("Schedule conflict.");
                }
            }
        }
    }
}