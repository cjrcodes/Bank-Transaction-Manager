package application;

/**
 * Checking class, a subclass of the Account class for checking accounts.
 * 
 * @author Christian Rodriguez, Yazhini Shanmugam
 *
 */

public class Checking extends Account {

	private boolean directDeposit;

	/**
	 * Default constructor for Checking, uses superclass constructor and sets direct
	 * deposit to false
	 */
	public Checking() {
		// TODO Auto-generated constructor stub
		super();
		this.directDeposit = false;
	}

	/**
	 * Constructor for Checking
	 * @param holder Profile of the holder of the account
	 * @param balance the amount in the account
	 * @param dateOpen date the account was opened
	 * @param directDeposit whether or not the account has direct deposit enabled
	 */
	public Checking(Profile holder, double balance, Date dateOpen, boolean directDeposit) {
		super(holder, balance, dateOpen);
		this.directDeposit = directDeposit;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Checks if object types are equal and if they have the same holder
	 * @param obj Object to be compared
	 * @return boolean true if objects are equal
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof Checking)) {
			return false;
		}
		Checking test = (Checking) obj;

		if ((this.getHolder().toString().equals((test.getHolder().toString())))) {
			return true;
		}
		
		return false;
	}
	/**
	 * Prints Checking account details to string
	 * @return String Checking account in string format
	 */
	public String toString() {
		return this.getAccountType() + this.getHolder().toString() + "*  $" + String.format("%.2f", this.getBalance()) + "*" + this.getOpenDate().toString() + this.getSpecialCondition();
	}

	/**
	 * Get the account, primarily for printing
	 * 
	 * @return String of the account type, in this case checking
	 */
	@Override
	public String getAccountType() {
		return "*Checking*";
	}
	
	/**
	 * Gets the account label, primarily for exporting
	 * @return String label associated with the account type
	 */
	@Override
	public String getAccountLabel() {
		return "C";
	}

	/**
	 * Return a special string condition for subclasses, like direct deposit
	 * 
	 * @return String in the subclass indicating whether there is a special
	 *         condition, in this case direct deposit
	 */
	@Override
	public String getSpecialCondition() {
		return this.directDeposit == true ? "*direct deposit account*" : "";
	}

	/**
	 * Gets the monthly interest of a checking account, 0.05
	 * 
	 * @return double 0.05 interest
	 */
	@Override
	public double monthlyInterest() {

		return 0.05;
	}

	/**
	 * Gets the monthly fee of a checking account, 0 if direct deposit and over 1500
	 * 
	 * @return double monthly fee
	 */
	@Override
	public double monthlyFee() {

		return this.directDeposit == true || this.getBalance() >= 1500 ? 0 : 25;
	}

	/**
	 * Checks if direct deposit is true, and returns a string corresponding to that. 
	 * @return String true string if direct deposit is enabled, false if not.
	 */
	@Override
	public String getConditionToFile() {
		// TODO Auto-generated method stub
		return this.directDeposit == true ? ",true" : ",false";
	}

}
