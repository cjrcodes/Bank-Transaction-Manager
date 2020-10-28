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
	 * Get the account, primarily for printing
	 * 
	 * @return String of the account type, in this case savings
	 */
	@Override
	public String getAccountType() {
		return "Savings";
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

}
