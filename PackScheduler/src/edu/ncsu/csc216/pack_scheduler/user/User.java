package edu.ncsu.csc216.pack_scheduler.user;
/**
 * User class defines user with all fields,
 * gets and sets first name, last name,
 * email address and password of user
 * 
 */
public abstract class User {

	/** Student's first name */
	protected String firstName;
	/** Student's last name */
	protected String lastName;
	/** Student's ID */
	protected String id;
	/** Students email */
	protected String email;
	/** Student's password hashed */
	protected String password;

	/**
	 * Defines a User with all needed fields.
	 * 
	 * @param firstName is the user's first name
	 * @param lastName is the user's last name
	 * @param id is the user's ID
	 * @param email is the user's email
	 * @param password user's password 
	 */
	public User(String firstName, String lastName, String id, String email, String password) {
	    setFirstName(firstName);
	    setLastName(lastName);
	    setId(id);
	    setEmail(email);
	    setPassword(password);
	}

	/**
	 * Gets the first name of the student
	 * 
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name of the student
	 * 
	 * @param firstName the first name of the student
	 * @throws IllegalArgumentException if the first name is empty or null
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || firstName.length() == 0) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}

	/**
	 * Gets the last name of the student
	 * 
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name of the student
	 * 
	 * @param lastName the last name of the student
	 * @throws IllegalArgumentException if the last name is empty or null
	 */
	public void setLastName(String lastName) {
		if (lastName == null || lastName.length() == 0) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}

	/**
	 * Gets the ID of the student
	 * 
	 * @return the ID
	 */
	public String getId() {
		return id;

	}

	/**
	 * Sets the ID of the student We set the method to private so that we prevent
	 * changing the ID after creation
	 * 
	 * @param id the ID of the student
	 * @throws IllegalArgumentException if the ID is empty or null
	 */
	protected void setId(String id) {
		if (id == null || id.length() == 0) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;

	}

	/**
	 * Gets the email of the student
	 * 
	 * @return the email of the student
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email of the student. It makes sure that the email is formatted correctly.
	 * 
	 * @param email the email of the student
	 * @throws IllegalArgumentException if the email is null, if the email is empty,
	 * if the email does not contain an '@' symbol
	 * or a '.', and if the '.' comes before the
	 * '@' symbol in the email
	 */
	public void setEmail(String email) {
		if (email == null || email.isEmpty()) {
			throw new IllegalArgumentException("Invalid email");
		}

		int indexOfAt = email.indexOf('@');
		int lastIndexOfDot = email.lastIndexOf('.');

		if (indexOfAt == -1 || lastIndexOfDot == -1) {
			throw new IllegalArgumentException("Invalid email");
		}

		if (lastIndexOfDot == email.length() - 1) {
			throw new IllegalArgumentException("Invalid email");
		}

		if (lastIndexOfDot < indexOfAt) {
			throw new IllegalArgumentException("Invalid email");
		}

		this.email = email;
	}

	/**
	 * Gets the password of the student
	 * 
	 * @return the password of the student
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * We are checking if the password is null or has a length of 0. 
	 * 
	 * @param password the password of the student
	 * @throws IllegalArgumentException if the password is either null or has a
	 * length of 0
	 */
	public void setPassword(String password) {
		if (password == null || password.length() == 0) {
			throw new IllegalArgumentException("Invalid password");
		}
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
	

}