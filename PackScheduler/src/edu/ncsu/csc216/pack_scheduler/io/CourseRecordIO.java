package edu.ncsu.csc216.pack_scheduler.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc217.collections.list.SortedList;

import java.io.FileInputStream;
import java.io.PrintStream;
import java.io.File;
import java.util.InputMismatchException;

/**
 * Reads Course records from text files.  Writes a set of CourseRecords to a file.
 * 
 * @author Sarah Heckman
 * @author Udita Pericharla
 */
public class CourseRecordIO {

    /**
     * Reads course records from a file and generates a list of valid Courses.  Any invalid
     * Courses are ignored.  If the file to read cannot be found or the permissions are incorrect
     * a File NotFoundException is thrown.
     * @param fileName file to read Course records from
     * @return a list of valid Courses
     * @throws FileNotFoundException if the file cannot be found or read
     */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
	    Scanner fileReader = new Scanner(new FileInputStream(fileName));  //Create a file scanner to read the file
	    SortedList<Course> courses = new SortedList<Course>(); //Create an empty array of Course objects
	    while (fileReader.hasNextLine()) { //While we have more lines in the file
	        try { //Attempt to do the following
	            //Read the line, process it in readCourse, and get the object
	            Course course = readCourse(fileReader.nextLine()); 

	            boolean duplicate = false;
	            //Look at all the courses in our list
	            for (int i = 0; i < courses.size(); i++) {
	                //Get the course at index i
	                Course current = courses.get(i);
	                //Check if the name and section are the same
	                if (course.getName().equals(current.getName()) &&
	                        course.getSection().equals(current.getSection())) {
	                    //It's a duplicate!
	                    duplicate = true;
	                    break; //We can break out of the loop
	                }
	            }
	            //If the course is NOT a duplicate
	            if (!duplicate) {
	                courses.add(course); //Add to the ArrayList!
	            } //Otherwise ignore
	        } catch (IllegalArgumentException e) {
	            //The line is invalid b/c we couldn't create a course, skip it!
	        }
	    }
	    //Close the Scanner b/c we're responsible with our file handles
	    fileReader.close();
	    //Return the ArrayList with all the courses we read!
	    return courses;
	}

/**
 * Processes single line into course object and expects
 * all tokens in the order of all aspects 
 * wanted such as name, title, etc. and if not exceptions will be thrown
 * @param line is a line to process
 * @return new course with all the elements/tokens in order
 * @throws IllegalArgumentException if there is invalid data or # of tokens
 * @throws InputMismatchException if tokens are not an integer
 */
    private static Course readCourse(String line) throws InputMismatchException {
        Scanner scan = new Scanner(line);
        scan.useDelimiter(",");
        Course c;
        try {
            String name = scan.next();
            String title = scan.next();
            String section = scan.next();
            int credits = scan.nextInt();
            String instructorId = scan.next();
            String meetingDays = scan.next();

            if ("A".equals(meetingDays)) {
                if (scan.hasNext()) {
                    scan.close();
                    throw new IllegalArgumentException("Invalid meeting days and times.");
                }
                c = new Course(name, title, section, credits, instructorId, meetingDays);
                scan.close();
                return c;
            }
            
            int startTime = 0;
            if(scan.hasNextInt()) {
            	startTime = scan.nextInt();
    
            } else {
            	scan.close();
            		throw new IllegalArgumentException("Missing invalid start time.");
            }
            
            int endTime = 0;
            if(scan.hasNextInt()) {
            	endTime = scan.nextInt();
    
            } else {
            	scan.close();
            		throw new IllegalArgumentException("Missing invalid end time.");
            }
     

            if (scan.hasNext()) {
                scan.close();
                throw new IllegalArgumentException("Invalid meeting days and times.");
            }
            c = new edu.ncsu.csc216.pack_scheduler.course.Course(name, title, section, credits, instructorId, meetingDays, startTime, endTime);
            scan.close();
            return c;
            
        } catch (InputMismatchException e) {
            scan.close();
            throw new IllegalArgumentException("Invalid course record.");
        }
    }

	/**
     * Writes the given list of Courses to 
     * @param fileName file to write schedule of Courses to
     * @param courses list of Courses to write
     * @throws IOException if cannot write to file
     */
	public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
	    PrintStream fileWriter = new PrintStream(new File(fileName));

	    for (int i = 0; i < courses.size(); i++) {
	        fileWriter.println(courses.get(i).toString());
	    }

	    fileWriter.close();
	}


}