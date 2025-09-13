package edu.ncsu.csc216.pack_scheduler.io;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner;



/**
 * This class is to support cases 3 and 4. 
 * It will read a file of student data and convert it to
 * each valid line into a Student object. It will also 
 * write student objects to a file in the right format.
 * 
 * @author Udita Pericharla
 */
public class StudentRecordIO {

    /**
     * Reads student records from a file provided. 
     *
     * @param fileName the name of the file to read from
     * @return is an ArrayList of valid Student objects
     * @throws FileNotFoundException if the file does not exist
     */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
        // Creates scanner to read from input stream
        Scanner fileScanner = new Scanner(new FileInputStream(fileName));
        // list to hold all VALID students
        SortedList<Student> students = new SortedList<Student>();

        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            try {
                Student student = processStudent(line);

                // Check for duplicate IDs
                boolean duplicate = false;
                for (int i = 0; i < students.size(); i++) {
                    Student s = students.get(i);
                    if (s.getId().equals(student.getId())) {
                        duplicate = true;
                        break;
                    }
                }

                if (!duplicate) {
                    students.add(student);
                }
            } catch (IllegalArgumentException e) {
                // Invalid student record
            }
        }

        fileScanner.close();
        return students;
    }

    /**
     * Parses a line of text into a Student object.
     *
     * @param line a comma-separated line of student data
     * @return a valid Student object created from the line
     * @throws IllegalArgumentException if the line is malformed or data invalid
     */
    private static Student processStudent(String line) {
        // Split the line into fields so there must be exactly 6 items
    	// instead of adding a different scanner to read each comma
        String[] parts = line.split(",", -1);
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid student record");
        }

        String firstName = parts[0];
        String lastName = parts[1];
        String id = parts[2];
        String email = parts[3];
        String password = parts[4];
        int maxCredits;
        try {
            maxCredits = Integer.parseInt(parts[5]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid student record");
        }

        // Let Student constructor perform field validation
        return new Student(firstName, lastName, id, email, password, maxCredits);
    }

    /**
     * Writes a list of students to the file. 
     *
     * @param fileName the name of the file to write to
     * @param students the list of students to save
     * @throws IOException if it cant write to the file
     */
    public static void writeStudentRecords(String fileName, SortedList<Student> students) throws IOException {
        try (PrintWriter writer = new PrintWriter(new File(fileName))) {
            for (int i = 0; i < students.size(); i++) {
                Student s = students.get(i);
                writer.println(s.toString());
            }
        }
    }
}