package application;

/**
 * AccountDatabase class, holds the current activated accounts and number of
 * accounts
 * 
 * @author Christian Rodriguez, Yazhini Shanmugam
 *
 */
public class AccountDatabase {

	private Account[] accounts;
	private int size;

	/**
	 * Default constructor for AccountDatebase, creates 5 slots for accounts and
	 * sets size to 0
	 */
	public AccountDatabase() {
		this.accounts = new Account[5];
		this.size = 0;
	}

	/**
	 * Finds an instance of an account
	 * 
	 * @param account to be found in the accounts array
	 * @return integer index of where the account is located in the array, else
	 *         return -1 if not found
	 */
	private int find(Account account) {

		if (this.size == 0) {
			// System.out.print("Accounts is empty\n");
			return -1;
		}

		for (int i = 0; i < this.accounts.length; i++) {

			if (this.accounts[i] != null && this.accounts[i].equals(account)) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * Grow the array by 5, called once the accounts capacity has been reached
	 */
	private void grow() {
		Account[] tempArray = this.accounts;
		this.accounts = new Account[this.size + 5];

		for (int i = 0; i < tempArray.length; i++) {
			this.accounts[i] = tempArray[i];
		}

	}

	/**
	 * Add an account to the database
	 * 
	 * @param account to be added to the database
	 * @return boolean true if the account did not exist before, false if the
	 *         account was already in the database
	 */
	public boolean add(Account account) {
		if (this.size == this.accounts.length) {
			// System.out.println("Growing size");
			this.grow();
		}

		if (this.find(account) != -1) {
			// System.out.println("Account already exists");
			return false;
		}

		// System.out.println("Looping");
		for (int i = 0; i < this.accounts.length; i++) {
			if (this.accounts[i] == null) {
				this.accounts[i] = account;
				this.size++;
				// System.out.println("Account added");
				return true;
			}
		}

		return true;
	} // return false if account exists

	/**
	 * Remove an account from the database
	 * 
	 * @param account to be removed from the database
	 * @return boolean true if the account was removed, false if the account does
	 *         not exists
	 */
	public boolean remove(Account account) {
		int accountIndex = this.find(account);
		if (accountIndex == -1) {
			return false;
		}

		this.accounts[accountIndex] = null;
		this.size--;

		// Lone element was removed or last element in the array was removed
		if (this.size == 0 || accountIndex == accounts.length - 1) {
			return true;
		}

		// Shift the array leftwards
		int lastIndex = 0;
		for (int i = accountIndex; i < this.accounts.length - 1; i++) {
			if (this.accounts[i + 1] == null) {
				return true;
			}
			lastIndex = i + 1;
			this.accounts[i] = this.accounts[i + 1];
		}

		if (this.accounts[lastIndex] != null) {
			this.accounts[lastIndex - 1] = this.accounts[lastIndex];
			this.accounts[lastIndex] = null;
		}

		return true;
	} // return false if account doesn’t exist

	public int getSize() {
		return this.size;
	}

	/**
	 * Deposit an amount to an account
	 * 
	 * @param account to have the amount added to
	 * @param amount  to be added to the account
	 * @return boolean true if account was found and the amount was deposited, false
	 *         if the account does not exist
	 */
	public boolean deposit(Account account, double amount) {
		if (this.find(account) == -1) {
			return false;
		}

		int accountIndex = this.find(account);

		this.accounts[accountIndex].credit(amount);

		return true;
	}

	/**
	 * Withdraw amount from an account
	 * 
	 * @param account to be withdrawn from
	 * @param amount  to be withdrawn from the account
	 * @return integer of whether withdrawal was successful (0), insufficient funds
	 *         (1), or the account doesn't exist (-1),
	 */
	public int withdrawal(Account account, double amount) {
		int accountIndex = this.find(account);

		if (accountIndex == -1) {
			return -1;
		}

		if (this.accounts[accountIndex].getBalance() - amount < 0) {
			return 1;
		}

		/*
		 * if (this.accounts[accountIndex].getAccountType().equals("*MoneyMarket*")) {
		 * this.accounts[accountIndex] = (MoneyMarket) this.accounts[accountIndex];
		 * 
		 * }
		 */

		this.accounts[accountIndex].debit(amount);

		return 0;
	}

	/**
	 * Return the accounts sorted by date opened
	 */
	private void sortByDateOpen() {
		int index = 0;
		Account temp = null;
		for (int i = 0; i < this.size - 1; i++) {
			index = i;
			for (int j = i + 1; j < this.size; j++) {
				if ((this.accounts[index].getOpenDate()).compareTo(this.accounts[j].getOpenDate()) == 1)
					index = j;
			}

			temp = this.accounts[index];
			this.accounts[index] = this.accounts[i];
			this.accounts[i] = temp;
		}

	} // sort in ascending order

	/**
	 * Return the accounts sorted by last name
	 */
	private void sortByLastName() {
		int index, i, j = 0;
		Account temp = null;
		for (i = 0; i < this.size - 1; i++) {
			index = i;
			for (j = i + 1; j < this.size; j++) {
				if (this.accounts[index].getHolder().getLastName().toUpperCase()
						.compareTo(this.accounts[j].getHolder().getLastName().toUpperCase()) > 0)
					index = j;
			}

			temp = this.accounts[index];
			this.accounts[index] = this.accounts[i];
			this.accounts[i] = temp;
			this.printAccounts();
		}

	} // sort in ascending order

	/**
	 * Print the accounts sorted by date opened
	 */
	public void printByDateOpen() {
		this.sortByDateOpen();
		this.printAccounts();
	}

	public String printByDateOpenToTextArea() {
		this.sortByDateOpen();
		return this.printAccountsOpenDateToTextArea();
	}

	/**
	 * Print the accounts sorted by date opened
	 */
	public void printByLastName() {
		this.sortByLastName();
		this.printAccountsToTextArea();
	}

	public String printByLastNameToTextArea() {
		this.sortByLastName();
		return this.printAccountsSortLastNameToTextArea();
	}

	/**
	 * Print all the accounts in the accounts array
	 */
	public void printAccounts() {
		// System.out.println("\n--Listing accounts in the database--\n");

		for (int i = 0; i < this.size; i++) {
			// System.out.println(this.accounts[i].toString());
		}

		// System.out.println("--end of listing--");
	}

	public String printAccountsToTextArea() {

		String text = "\n--Listing accounts in the database--\n";

		for (int i = 0; i < this.size; i++) {
			text += (this.accounts[i].toString() + "\n");
		}

		text += "--end of listing--\n";

		return text;
	}

	public String printAccountsSortLastNameToTextArea() {

		String text = "\n--Printing statements by last name--\n";

		for (int i = 0; i < this.size; i++) {
			text += (this.accounts[i].toString() + "\n");
			text += ("-interest: $ " + String.format("%.2f", this.accounts[i].monthlyInterest()) + "\n");
			text += ("-fee: $ " + String.format("%.2f", this.accounts[i].monthlyFee()) + "\n");
			text += ("-new balance: $ " + String.format("%.2f", (this.accounts[i].getBalance()
					- this.accounts[i].monthlyFee() + this.accounts[i].monthlyInterest())) + "\n\n");
		}

		text += "--end of listing--\n";

		return text;
	}

	public String printAccountsOpenDateToTextArea() {

		String text = "\n--Printing statements by date opened--\n";

		for (int i = 0; i < this.size; i++) {
			text += (this.accounts[i].toString() + "\n");
			text += ("-interest: $ " + String.format("%.2f", this.accounts[i].monthlyInterest()) + "\n");
			text += ("-fee: $ " + String.format("%.2f", this.accounts[i].monthlyFee()) + "\n");
			text += ("-new balance: $ " + String.format("%.2f", (this.accounts[i].getBalance()
					- this.accounts[i].monthlyFee() + this.accounts[i].monthlyInterest())) + "\n\n");
		}

		text += "--end of listing--\n";

		return text;
	}

	/*
	 * Get
	 */
	public Account[] getAccounts() {
		return this.accounts;
	}

	public int getAccountIndex(Account account) {
		return this.find(account);
	}

	/*
	 * public static void main(String [] args) { AccountDatabase db = new
	 * AccountDatabase(); Account c = new Checking(new Profile("Christian",
	 * "Rodrihguex"), 2,new Date(1,2,2000), false);
	 * 
	 * Account d = new Savings(new Profile("Christian", "Rodrihguex"), 2,new
	 * Date(1,2,2000), false);
	 * 
	 * Account e = new Checking(new Profile("Christian", "Rodrihguex"), 2,new
	 * Date(1,2,2000), false);
	 * 
	 * 
	 * System.out.println(db.add(c));
	 * 
	 * db.printAccounts();
	 * 
	 * db.add(d);
	 * 
	 * db.add(e);
	 * 
	 * db.add(d);
	 * 
	 * 
	 * System.out.println(db.find(c));
	 * 
	 * System.out.println(db.size);
	 * 
	 * db.printAccounts();
	 * 
	 * 
	 * }
	 */

}
