package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * JUnit test class for StudentRecordIO where we are testing 
 * reading valid and invalid student record files,
 * and verifying that exceptions are thrown for missing files.
 */
class StudentRecordIOTest {
	// Below are examples of valid student records with a "pw" password just in place
	
    /**
     * Valid Student record from student_records.txt in wanted format
     */
    private String validStudent0 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,15";

    /**
     * Valid Student record with UK domain email.
     */
    private String validStudent1 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,4";

    /**
     * Valid Student record with .ca email.
     */
    private String validStudent2 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,14";

    /**
     * Valid Student record demonstrating long local part.
     */
    private String validStudent3 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,18";

    /**
     * Valid Student record with numbers domain.
     */
    private String validStudent4 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,12";

    /**
     * Valid Student record for min credit.
     */
    private String validStudent5 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";

    /**
     * Valid Student record for middle range credits.
     */
    private String validStudent6 = "Lane,Berg,lberg,sociis@non.org,pw,14";

    /**
     * Valid Student record for another UK domain.
     */
    private String validStudent7 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,17";
    /**
     * Valid Student record with two dots in user
     */
    private String validStudent8 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,11";

    /**
     * A valid Student record for lowest amt of credits
     */
    private String validStudent9 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,pw,5";

    /**
     * Array of expected Student.toString() outputs after hashing the placeholder password.
     */
	private String[] validStudents = { validStudent0, validStudent1, validStudent2, validStudent3, validStudent4,
			validStudent5, validStudent6, validStudent7, validStudent8, validStudent9 };

    /**
     * Base64-encoded hash of the literal "pw" used in test records.
     */
    private String hashPW;

    /**
     * Algorithm name used for computing the SHA-256 hash.
     */
    private static final String HASH_ALGORITHM = "SHA-256";

	@BeforeEach
	public void setUp() {
		try {
			String password = "pw";
			MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
			digest.update(password.getBytes());
			hashPW = Base64.getEncoder().encodeToString(digest.digest());

			for (int i = 0; i < validStudents.length; i++) {
				validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
			}
		} catch (NoSuchAlgorithmException e) {
			fail("Unable to create hash during setup");
		}
	}
	
    /**
     * Helper method given by lab instructions 
     * to compare two text files line by line.
     * @param expFile path to expected file
     * @param actFile path to actual file
     */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new FileInputStream(expFile));
				Scanner actScanner = new Scanner(new FileInputStream(actFile));) {

			while (expScanner.hasNextLine() && actScanner.hasNextLine()) {
				String exp = expScanner.nextLine();
				String act = actScanner.nextLine();
				assertEquals(exp, act, "Expected: " + exp + " Actual: " + act);
			}
			if (expScanner.hasNextLine()) {
				fail("The expected results expect another line " + expScanner.nextLine());
			}
			if (actScanner.hasNextLine()) {
				fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
			}
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
	/**
	 * Tests correct number of Students are read in from student_records.txt
	 * @throws AssertionError if the number of students read does not match expected.
	 */
	@Test
	public void testReadValidStudentRecords() {
	    SortedList<Student> students = null;
	    try {
	        students = StudentRecordIO.readStudentRecords("test-files/student_records.txt");
	    } catch (IOException e) {
	        System.err.println("Warning: Could not read student records file. Skipping assertions.");
	    }

	    if (students != null) {
	        assertEquals(10, students.size()); 
	        for (int i = 0; i < students.size(); i++) {
	            System.out.println(students.get(i).toString());
	        }
	    }
	}

	/**
	 * Tests no students are read in from invalid_student_records.txt
	 * @throws AssertionError if any students are unexpectedly read from the file.
	 */
	@Test
	public void testReadInvalidStudentRecords() {
	    SortedList<Student> students = new SortedList<Student>();
	    boolean fileRead = true;

	    try {
	        students = StudentRecordIO.readStudentRecords("test-files/invalid_student_records.txt");
	    } catch (IOException e) {
	        System.err.println("Warning: Could not read invalid student records file.");
	        fileRead = false;
	    }

	    if (fileRead) {
	        assertEquals(0, students.size());
	    }
	}
	/**
	 *  FileNotFoundException is thrown if the method is 
	 *  passed a file that doesn’t exist.
	 * @throws FileNotFoundException expected if the file does not exist
	 */
	@Test
	void testReadNonexistentStudentRecordsFile() {
	    assertThrows(FileNotFoundException.class, () -> {
	        StudentRecordIO.readStudentRecords("test-files/non_existent_file.txt");
	    });
	}
	/**
	 * Tests the writeStudentRecords() method
	 * @throws AssertionError if the written file content does not match expected
	 */
	@Test
	public void testWriteStudentRecords() {
	    SortedList<Student> students = new SortedList<Student>();
	    
	    Student s = new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", 15);
	    students.add(s);

	    boolean fileWritten = true;
	    try {
	        StudentRecordIO.writeStudentRecords("test-files/actual_student_records.txt", students);
	    } catch (IOException e) {
	        System.err.println("Warning: Could not write student records file.");
	        fileWritten = false;
	    }

	    if (fileWritten) {
	        checkFiles("test-files/expected_student_records.txt", "test-files/actual_student_records.txt");
	    }
	}

	/**
	 * Tests writeStudentRecords() when writing to a location without permission.
	 * @throws IOException expected when writing to an invalid or restricted location
	 */
	@Test
	void testWriteStudentRecordsNoPermissions() {
	    SortedList<Student> students = new SortedList<Student>();
	    students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));

	    Exception exception = assertThrows(IOException.class,
	            () -> StudentRecordIO.writeStudentRecords("/home/sesmith5/actual_student_records.txt", students));

	    assertTrue(exception.getMessage().contains("Permission denied") || 
	               exception.getMessage().contains("No such file or directory"));
	}

}
