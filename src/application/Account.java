package application;

/**
 * Account class, provides information about the holder, their balance, and the
 * date the account was opened.
 * 
 * @author Christian Rodriguez, Yazhini Shanmugam
 *
 */

public abstract class Account {

	private Profile holder;
	private double balance;
	private Date dateOpen;

	/**
	 * Default constructor for account, sets holder to null, balance to 0, and date
	 * to the default Date (1/1/2000)
	 */
	public Account() {
		this.holder = null;
		this.balance = 0;
		this.dateOpen = new Date();
	}

	/**
	 * Constructor for Account, assigns the account holder, balance, and date open
	 * based on input
	 * 
	 * @param holder   Profile of the account holder
	 * @param balance  current balance of the account holder
	 * @param dateOpen date the account was opened
	 */
	public Account(Profile holder, double balance, Date dateOpen) {
		this.holder = holder;
		this.balance = balance;
		this.dateOpen = dateOpen;
	}

	/**
	 * Check whether all data fields values of the two objects are the same.
	 * 
	 * @param obj to be checked if it is actually an account that exists
	 * @return true if the given object has all data fields and values of the
	 *         account, otherwise, return false
	 * 
	 **/
	public boolean equals(Object obj) {
		if (!(obj instanceof Account)) {
			return false;
		}
		Account test = (Account) obj;

		if ((this.getHolder().toString().equals((test.getHolder().toString())))) {
			return true;
		}

		return false;
	}

	/**
	 * Decrease the balance by amount
	 * 
	 * @param amount to be deducted from the balance
	 */
	public void debit(double amount) {
		this.balance -= amount;

	}

	/**
	 * Increase the balance by amount
	 * 
	 * @param amount to be added to the balance
	 */
	public void credit(double amount) {
		this.balance += amount;
	}

	/**
	 * Prints the account details to string
	 * 
	 * @return String of account details
	 */
	public String toString() {
		return this.holder.toString() + " " + Double.toString(this.balance) + " " + this.dateOpen.toString();
	}

	/**
	 * Gets the balance of the account
	 * 
	 * @return double of the account balance
	 */
	public double getBalance() {
		return this.balance;
	}

	/**
	 * Get the holder of the account
	 * 
	 * @return Profile the holder of the account
	 */
	public Profile getHolder() {
		return this.holder;
	}

	/**
	 * Get the open date of the account
	 * 
	 * @return Date open date of the account
	 */
	public Date getOpenDate() {
		return this.dateOpen;
	}

	/**
	 * Return a special string condition for subclasses, like direct deposit,
	 * special savings, or number of withdrawals
	 * 
	 * @return String in the subclass indicating whether there is a special
	 *         condition
	 */
	public abstract String getSpecialCondition();

	/**
	 * Get the subclass type, implemented method by subclasses
	 * 
	 * @return String of the subclass type (Savings, Checking, MoneyMarket)
	 */
	public abstract String getAccountType();

	/**
	 * Gets the account label, primarily for exporting
	 * @return String label associated with the account type
	 */
	public abstract String getAccountLabel();

	/**
	 * Checks for any special conditions on the account type
	 * @return String string of anything to be appended while printing
	 */
	public abstract String getConditionToFile();

	/**
	 * Returns monthly interest, must be implemented by child classes as it is an
	 * abstract method
	 * 
	 * @return double of the monthly interest rate
	 */
	public abstract double monthlyInterest();

	/**
	 * Returns monthly fee, must be implemented by child classes as it is an
	 * abstract method
	 * 
	 * @return double of the monthly fee
	 */
	public abstract double monthlyFee();

	/*
	 * public static void main(String[] args) { Profile testProfile = new
	 * Profile("James", "Bond"); Date testDate = new Date(1, 1, 2000);
	 * System.out.println(testProfile.toString()); Checking c = new Checking();
	 * c.toString(); }
	 */
}
