package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;
import java.io.IOException;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * This class shows that CourseCatalog uses a SortedList to keep track
 * of Course objects.
 * 
 *  It's job is to allow for the adding, removing, loading, saving, and getting of
 * course details.
 * 
 * Received help fixing errors that delt with files not being recognized 
 * on June 10th, 10am. 
 * 
 * @author Udita Pericharla
 */
public class CourseCatalog {
	//

    /** Catalog of courses */
    private SortedList<Course> catalog;
    // testing commit

    /**
     * Constructor initializes catalog as an empty list.
     */
    public CourseCatalog() {
        newCourseCatalog();
    }

    /**
     * Clears current catalog and creates a new empty one.
     */
    public void newCourseCatalog() {
        catalog = new SortedList<Course>();
    }

    /**
     * Loads course data from a file.
     * @param fileName is the name of the file
     */
    public void loadCoursesFromFile(String fileName) {
        try {
            SortedList<Course> loadedCourses = new SortedList<Course>();
            SortedList<Course> tempList = CourseRecordIO.readCourseRecords(fileName);
            for (int i = 0; i < tempList.size(); i++) {
                loadedCourses.add(tempList.get(i));
            }
            catalog = loadedCourses;
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Unable to read file " + fileName);
        }
    }

    /**
     * Adds a course to the catalog
     * @param name the course name
     * @param title the course title
     * @param section the section number
     * @param credits the credit hours
     * @param instructorId the instructor's unity ID
     * @param meetingDays the meeting days
     * @param startTime the start time
     * @param endTime the end time
     * @return true if the course was added
     */
    public boolean addCourseToCatalog(String name, String title, String section, int credits, 
            String instructorId, String meetingDays, int startTime, int endTime) {
        Course newCourse = new Course(name, title, section, credits, instructorId, meetingDays, startTime, endTime);
        
        for (int i = 0; i < catalog.size(); i++) {
            Course existing = catalog.get(i);
            if (existing.getName().equals(name) && existing.getSection().equals(section)) {
                return false;
            }
        }
        
        catalog.add(newCourse);
        return true;
    }

    /**
     * Removes a course from the catalog.
     * @param name is the course name
     * @param section is the course section
     * @return true if removed, false if not
     */
    public boolean removeCourseFromCatalog(String name, String section) {
        for (int i = 0; i < catalog.size(); i++) {
            Course c = catalog.get(i);
            if (c.getName().equals(name) && c.getSection().equals(section)) {
                catalog.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Gets a course from the catalog based on name and section.
     * @param name is the course name
     * @param section is the course section
     * @return the Course or null if not found
     */
    public Course getCourseFromCatalog(String name, String section) {
        for (int i = 0; i < catalog.size(); i++) {
            Course c = catalog.get(i);
            if (c.getName().equals(name) && c.getSection().equals(section)) {
                return c;
            }
        }
        return null;
    }

    /**
     * Returns a string array of all courses in the catalog.
     * @return array with courses with name, section, title, and meeting info
     */
    public String[][] getCourseCatalog() {
        String[][] catalogArray = new String[catalog.size()][4];

        for (int i = 0; i < catalog.size(); i++) {
            Course c = catalog.get(i);
            catalogArray[i] = c.getShortDisplayArray();
        }

        return catalogArray;
    }

    /**
     * Saves the catalog to a file.
     * @param fileName is the name of the file to save to
     */
    public void saveCourseCatalog(String fileName) {
        SortedList<Course> list = new SortedList<Course>();
        for (int i = 0; i < catalog.size(); i++) {
            list.add(catalog.get(i));
        }
        try {
            CourseRecordIO.writeCourseRecords(fileName, list);
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to write to file " + fileName);
        }
    }
}
