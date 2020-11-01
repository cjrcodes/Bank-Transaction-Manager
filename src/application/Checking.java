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

	public Checking(Profile holder, double balance, Date dateOpen, boolean directDeposit) {
		// TODO Auto-generated constructor stub
		super(holder, balance, dateOpen);
		this.directDeposit = directDeposit;
	}

	/**
	 * Get the account, primarily for printing
	 * 
	 * @return String of the account type, in this case checking
	 */
	@Override
	public String getAccountType() {
		return "Checking";
	}

	/**
	 * Return a special string condition for subclasses, like direct deposit
	 * 
	 * @return String in the subclass indicating whether there is a special
	 *         condition, in this case direct deposit
	 */
	@Override
	public String getSpecialCondition() {
		return directDeposit == true ? "*direct deposit account*" : "";
	}

	/**
	 * Gets the monthly interest of a checking account, 0.05
	 * 
	 * @return double 0.05 interest
	 */
	@Override
	public double monthlyInterest() { return 0.05; }

	/**
	 * Gets the monthly fee of a checking account, 0 if direct deposit and over 1500
	 * 
	 * @return double monthly fee
	 */
	@Override
	public double monthlyFee() { return this.directDeposit == true || this.getBalance() >= 1500 ? 0 : 25; }

}
