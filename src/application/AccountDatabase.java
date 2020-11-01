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
		accounts = new Account[5];
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

		for (int i = 0; i < this.accounts.length; i++) {

			if (this.accounts[i] != null && this.accounts[i] == account) {
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
			this.grow();
		}

		if (this.find(account) != -1) {
			return false;
		}

		for (int i = 0; i < this.size; i++) {
			if (this.accounts[i] == null) {
				this.accounts[i] = account;
				this.size++;
				return true;
			}
		}

		return true;
	} // return false if account exists

	/**
	 * Remove an account from the database
	 * 
	 * @param account to be removed from the database
	 * @return boolean true if the account was removed, false if the account already
	 *         exists
	 */
	public boolean remove(Account account) {
		int accountIndex = this.find(account);
		if (accountIndex == -1) {
			return false;
		}

		this.accounts[accountIndex] = null;
		int lastIndex = -1;
		for (int i = accountIndex; i < this.accounts.length - 1; i++) {
			if (this.accounts[i + 1] == null) {
				break;
			}
			lastIndex = i + 1;
			this.accounts[i] = this.accounts[i + 1];
		}

		if (this.accounts[lastIndex] != null) {
			this.accounts[lastIndex - 1] = this.accounts[lastIndex];
			this.accounts[lastIndex] = null;
		}

		return true;
	} // return false if account doesn�t exist

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

		account.credit(amount);

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

		if (account.getBalance() - amount < 0) {
			return 1;
		}

		if (account.getAccountType().equals("MoneyMarket")) {
			account = (MoneyMarket) account;

		}

		account.debit(amount);

		return 0;
	}

	/**
	 * Return the accounts sorted by date opened
	 */
	private void sortByDateOpen() {
		int index = 0;
		Account temp = null;
		for (int i = 0; i < this.size-1; i++){
			index = i;
			for (int j = i+1; j < this.size; j++){
				if ( (this.accounts[i].getOpenDate()).compareTo(this.accounts[j].getOpenDate()) == 1 )
					index = j;
			}
			temp = this.accounts[index];
			this.accounts[index] = this.accounts[i];
			this.accounts[i] = temp;
		}
	}

	/**
	 * Return the accounts sorted by last name
	 */
	private void sortByLastName() {
		int index, space, space2 = 0;
		String last, last2 = null;
		Account temp = null;
		for (int i = 0; i < this.size-1; i++){
			index = i;
			for (int j = i+1; j < this.size; j++){
				space = this.accounts[i].getHolder().toString().indexOf(" ");
				space2 = this.accounts[j].getHolder().toString().indexOf(" ");
				last = this.accounts[j].getHolder().toString().substring(space);
				last2 = this.accounts[j].getHolder().toString().substring(space2);
				if ( last.compareTo(last2) > 0 )
					index = j;
			}
			temp = this.accounts[index];
			this.accounts[index] = this.accounts[i];
			this.accounts[i] = temp;
		}
	} // sort in ascending order

	/**
	 * Print the accounts sorted by date opened
	 */
	public void printByDateOpen() {
		this.sortByDateOpen();
		this.printAccounts();
	}

	/**
	 * Print the accounts sorted by date opened
	 */
	public void printByLastName() {
		this.sortByLastName();
		this.printAccounts();
	}

	/**
	 * Print all the accounts in the accounts array
	 */
	public void printAccounts() {
		System.out.println("\n--Listing accounts in the database--\n");

		for (int i = 0; i < this.size; i++) {
			System.out.println("*" + this.accounts[i].getAccountType() + "*" + this.accounts[i].getHolder().toString()
					+ "*" + " $" + String.format("%.2f", this.accounts[i].getBalance()) + "*"
					+ this.accounts[i].getOpenDate().toString() + this.accounts[i].getSpecialCondition());
		}

		System.out.println("--end of listing--");
	}

}
