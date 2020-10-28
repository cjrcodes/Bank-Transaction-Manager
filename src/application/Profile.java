package application;

/**
 * Profile class, a class used to provide user profiles for accounts. Includes a
 * first name and a last name.
 * 
 * @author Christian Rodriguez, Yazhini Shanmugam
 *
 */
public class Profile {

	private String fname;
	private String lname;

	/**
	 * Default constructor for Profile, blank names
	 */
	public Profile() {
		this.fname = "";
		this.lname = "";
	}

	/**
	 * Constructor for Profile, includes first name and last name
	 * 
	 * @param fname first name of the user account
	 * @param lname last name of the user account
	 */
	public Profile(String fname, String lname) {
		this.fname = fname;
		this.lname = lname;
	}

	/**
	 * toString method for Profile, prints first name and last name
	 * 
	 * @return String of the first name and last name
	 */
	public String toString() {
		return this.fname + " " + this.lname;
	}

}
