package application;

/**
 * Savings class, a subclass of account designed for savings accounts. Includes
 * a loyalty bonus.
 * 
 * @author Christian Rodriguez, Yazhini Shanmugam
 *
 */

public class Savings extends Account {

	private boolean isLoyal;

	/**
	 * Default constructor for Savings
	 */
	public Savings() {
		// TODO Auto-generated constructor stub
		super();
		this.isLoyal = false;
	}

	/**
	 * Constructor for Savings, includes holder, balance, date opened, and if the
	 * account user is loyal
	 * 
	 * @param holder   Profile of the account holder
	 * @param balance  current balance of the account holder
	 * @param dateOpen date the account was opened
	 * @param isLoyal  whether the account holder is loyal
	 */

	public Savings(Profile holder, double balance, Date dateOpen, boolean isLoyal) {
		super(holder, balance, dateOpen);
		this.isLoyal = isLoyal;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Checks if object types are equal and if they have the same holder
	 * @param obj Object to be compared
	 * @return boolean true if objects are equal
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof Savings)) {
			return false;
		}
		Savings test = (Savings) obj;

		if ((this.getHolder().toString().equals((test.getHolder().toString())))) {
			return true;
		}

		return false;
	}

	
	/**
	 * Prints Savings account details to string
	 * @return String Savings account in string format
	 */
	public String toString() {
		return this.getAccountType() + this.getHolder().toString() + "*  $" + String.format("%.2f", this.getBalance())
				+ "*" + this.getOpenDate().toString() + this.getSpecialCondition();
	}

	/**
	 * Get the account, primarily for printing
	 * 
	 * @return String of the account type, in this case savings
	 */
	@Override
	public String getAccountType() {
		return "*Savings*";
	}

	/**
	 * Return a special string condition for subclasses, like a special savings
	 * account
	 * 
	 * @return String in the subclass indicating whether there is a special
	 *         condition, in this case special savings account
	 */
	@Override
	public String getSpecialCondition() {
		return isLoyal == true ? "*special Savings account*" : "";
	}

	/**
	 * Gets the monthly interest of a checking account, 0.05
	 * 
	 * @return double 0.35 interest if loyal, 0.25 if not
	 */
	@Override
	public double monthlyInterest() {
		// TODO Auto-generated method stub
		return this.isLoyal == true ? 0.35 : 0.25;
	}

	/**
	 * Gets the monthly fee of a savings account
	 * 
	 * @return double monthly fee, 0 if over 300
	 */
	@Override
	public double monthlyFee() {
		// TODO Auto-generated method stub
		return this.getBalance() >= 300 ? 0 : 5;
	}

	/**
	 * Gets the account label, primarily for exporting
	 * @return String label associated with the account type
	 */
	@Override
	public String getAccountLabel() {
		// TODO Auto-generated method stub
		return "S";
	}

	@Override
	public String getConditionToFile() {
		// TODO Auto-generated method stub
		return this.isLoyal == true ? ",true" : ",false";
	}

}
