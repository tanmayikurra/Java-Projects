package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
/**
 * Manages course registration system including user validation,
 * course catalog, and student directory. 
 */
public class RegistrationManager {
	/** Singleton instance of RegistrationManager */
	private static RegistrationManager instance;
	
    /** Catalog of courses */
	  private CourseCatalog courseCatalog;
	  
	    /** Directory of students */
	private StudentDirectory studentDirectory;
	
    /** Registrar user */
	  private User registrar;
	  
	    /** Currently logged in user */
	   private User currentUser;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
    /** Registrar properties file name */
	private static final String PROP_FILE = "registrar.properties";

    /**
     * Privately creates registrar, course catalog and student directory.
     */
	private RegistrationManager() {
	    createRegistrar();
	    courseCatalog = new CourseCatalog();
	    studentDirectory = new StudentDirectory();
	}
	
    /**
     * Creates registrar user from properties file.
     * @throws IllegalArgumentException if cannot create registrar
     */
	private void createRegistrar() {
		Properties prop = new Properties();
		
		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);
			
			String hashPW = hashPW(prop.getProperty("pw"));
			
			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"), prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
	}
	
    /**
     * Hashes the given password using SHA-256 algorithm.
     * @param pw password to hash
     * @return hashed password
     * @throws IllegalArgumentException if hashing fails
     */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}
	
    /**
     * Gets the singleton instance of RegistrationManager.
     * @return RegistrationManager instance
     */
	public static synchronized RegistrationManager getInstance() {
		  if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}

    /**
     * Gets the course catalog.
     * @return course catalog
     */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}
    /**
     * Gets the student directory.
     * @return student directory
     */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}
    /**
     * Attempts to log in a user with given credentials.
     * @param id user ID
     * @param password user password
     * @return true if login successful, false otherwise
     */
	public boolean login(String id, String password) {
	    Student s = studentDirectory.getStudentById(id);
	    String localHashPW = hashPW(password);

	    // First check if it's a student
	    if (s != null) {
	        if (s.getPassword().equals(localHashPW)) {
	            currentUser = s;
	            return true;
	        } else {
	            return false; // Wrong password for student
	        }
	    }

	    // Now check registrar
	    if (registrar != null && registrar.getId().equals(id)) {
	        if (registrar.getPassword().equals(localHashPW)) {
	            currentUser = registrar;
	            return true;
	        } else {
	            return false; // Wrong password for registrar
	        }
	    }
	    throw new IllegalArgumentException("User doesn't exist.");
	}

	
    /**
     * Logs out the current user.
     */
	public void logout() {
	    currentUser = null;  
	}
	
    /**
     * Gets the currently logged in user.
     * @return current user or null if no user logged in
     */
    public User getCurrentUser() {
        return currentUser;   
    }
    
    /**
     * Clears all data (course catalog and student directory).
     */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
	}
	
	/**
	 * Creates static registrar class that extends user.
	 */
	private static class Registrar extends User {
		/**
		 * Create a registrar user.
		 * @param firstName first name of user
		 * @param lastName last name of user
		 * @param id id of user
		 * @param email email of user
		 * @param hashPW hashed password of user
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}
	}
}