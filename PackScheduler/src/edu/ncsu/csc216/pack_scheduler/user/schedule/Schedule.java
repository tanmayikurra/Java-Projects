package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;
/**
 * Class for student’s schedule.  
 * 
 * Keeps a list of courses (no nulls or duplicates) and a title.
 * Provides methods to add, remove, reset, and list scheduled courses.
 * @atuhor udita & tanmayi
 */
public class Schedule {
    /** 
     * The list of courses in this 
     * schedule (uses a custom ArrayList).
     */
	private ArrayList<Course> schedule;
    
    /** A title for this schedule. */
    private String title;

    /**
     * Constructs an empty schedule with the default title "My Schedule".
     */
    public Schedule() {
        // start with an empty list of courses
        this.schedule = new ArrayList<>();
        // default title as asked for
        this.title = "My Schedule";
    }
    /**
     * Class adds a course to the schedule.
     * 
     * Null courses are not allowed.Courses that has issues within 
     * time with any existing course will be rejected.
     * Duplicate courses (same name & section) are rejected.
     *  
     * @param c is the Course to add
     * @return true if the course was added successfully
     * @throws NullPointerException if the given course is null
     * @throws IllegalArgumentException if the course conflicts or is already in the schedule
     */
    public boolean addCourseToSchedule(Course c) {
        // reject null
        if (c == null) {
            throw new NullPointerException("Cannot add null course to schedule.");
        }
        // check for time conflicts with existing courses
        for (Course existing : schedule) {
            try {
                c.checkConflict(existing);
            } catch (ConflictException e) {
                // conflict found
                throw new IllegalArgumentException("The course cannot be added due to a conflict.");
            }
        }
        // attempt to add
        // finding duplicate is sorted by ArrayList.add()
        try {
            schedule.add(c);
            return true;
        } catch (IllegalArgumentException e) {
            // thrown if duplicate
            throw new IllegalArgumentException("You are already enrolled in " + c.getName() + ".");
        }
    }

    /**
     * Removes a course from the schedule.
     * @param c is the Course to remove
     * @return true if the course was present and removed; false otherwise
     */
    public boolean removeCourseFromSchedule(Course c) {
        // null means nothing to remove
        if (c == null) {
            return false;
        }
        // rely on ArrayList.remove(Object) to find and remove
        return schedule.remove(c);
    }

    /**
     * Clears all courses from this schedule 
     * and resets the title back to "My Schedule".
     */
    public void resetSchedule() {
        // create new empty list
        this.schedule = new ArrayList<>();
        // restore original title
        this.title = "My Schedule";
    }

    /**
     * Gets the scheduled courses in a table form:
     * each row is the Course.getShortDisplayArray() of a course.
     * @return an array where each sub array has personal details such as 
     * name, section, title, etc. 
     */
    public String[][] getScheduledCourses() {
        String[][] table = new String[schedule.size()][];
        for (int i = 0; i < schedule.size(); i++) {
            // pull the short display array from each Course
            table[i] = schedule.get(i).getShortDisplayArray();
        }
        return table;
    }
    // Check

    /**
     * Gets the title of this schedule.
     * @return the schedule’s title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets a new title for this schedule.
     * @param title is the new title (must not be null)
     * @throws IllegalArgumentException if title is null
     */
    public void setTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null.");
        }
        this.title = title;
    }
}
