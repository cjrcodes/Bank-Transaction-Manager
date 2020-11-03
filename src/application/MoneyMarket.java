package application;

/**
 * MoneyMarket class, a subclass of the Account class for a money market
 * account. Includes the number of withdrawals.
 * 
 * @author Christian Rodriguez, Yazhini Shanmugam
 *
 */

public class MoneyMarket extends Account {

	private int withdrawals;

	/**
	 * Default constructor for MoneyMarket
	 */
	public MoneyMarket() {
		// TODO Auto-generated constructor stub
		super();
		this.withdrawals = 0;
	}

	/**
	 * MoneyMarket constructor
	 * 
	 * @param holder   of the account
	 * @param balance  of the account
	 * @param dateOpen date the account was opened
	 */
	public MoneyMarket(Profile holder, double balance, Date dateOpen) {
		super(holder, balance, dateOpen);
		this.withdrawals = 0;
		// TODO Auto-generated constructor stub
	}

	public MoneyMarket(Profile holder, double balance, Date dateOpen, int withdrawals) {
		super(holder, balance, dateOpen);
		this.withdrawals = withdrawals;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Increases withdrawal amount
	 */
	public void withdraw() {
		this.withdrawals++;
	}
	/**
	 * Checks if object types are equal and if they have the same holder
	 * @param obj Object to be compared
	 * @return boolean true if objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MoneyMarket)) {
			return false;
		}
		MoneyMarket test = (MoneyMarket) obj;

		if ((this.getHolder().toString().equals((test.getHolder().toString())))) {
			return true;
		}

		return false;
	}

	@Override
	public void debit(double amount) {
		super.debit(amount);
		this.withdrawals++;
	}

	@Override
	public String toString() {
		return this.getAccountType() + this.getHolder().toString() + "*  $" + String.format("%.2f", this.getBalance())
				+ "*" + this.getOpenDate().toString() + this.getSpecialCondition();
	}

	public void debit(Double amount) {
		this.debit(amount);
		this.withdraw();
	}

	/**
	 * Get the account, primarily for printing
	 * 
	 * @return String of the account type, in this case money market
	 */
	@Override
	public String getAccountType() {
		return "*MoneyMarket*";
	}

	/**
	 * Return a special string condition for subclasses, like withdrawals
	 * 
	 * @return String in the subclass indicating whether there is a special
	 *         condition, in this case the amount of withdrawals
	 */
	@Override
	public String getSpecialCondition() {
		return this.withdrawals > 0 ? String.format("*%d withdrawal(s)*", this.withdrawals) : "";
	}

	/**
	 * Return the monthly interest for an account
	 * 
	 * @return double 0.65 interest rate
	 */
	@Override
	public double monthlyInterest() {
		// TODO Auto-generated method stub
		return 0.65;
	}

	/**
	 * Return the monthly fee for an account
	 * 
	 * @return double return the monthly fee for money market account.
	 */
	@Override
	public double monthlyFee() {
		// TODO Auto-generated method stub
		return this.withdrawals < 6 && this.getBalance() >= 2500 ? 0 : 12;
	}

	/**
	 * Gets the account label, primarily for exporting
	 * @return String label associated with the account type
	 */
	@Override
	public String getAccountLabel() {
		// TODO Auto-generated method stub
		return "M";
	}
	/**
	 * Checks for any special conditions on the account type
	 * @return String string of anything to be appended while printing
	 */
	@Override
	public String getConditionToFile() {
		// TODO Auto-generated method stub
		return this.getSpecialCondition() != "" ? "," + this.withdrawals : ",0";
	}

	/*
	 * public static void main(String [] args) { MoneyMarket m = new MoneyMarket();
	 * System.out.println(m.getClass()); }
	 */
}
