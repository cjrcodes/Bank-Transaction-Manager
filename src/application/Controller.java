package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Arrays;

import com.sun.tools.javac.util.StringUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.Alert.AlertType;

/**
 * Controller class, controls the logic for the model classes and controls changes to the database. Holds all FXML controls and document objects.
 * 
 * @author Christian Rodriguez, Yazhini Shanmugam
 *
 */

public class Controller {

	static AccountDatabase accounts;

	private int characterLimit = 30;

	// Open/Close Account Tab
	@FXML
	private RadioButton checking;

	@FXML
	private RadioButton savings;

	@FXML
	private RadioButton moneyMarket;

	@FXML
	private ToggleGroup accountType;

	@FXML
	private CheckBox isLoyal;

	@FXML
	private CheckBox directDeposit;

	@FXML
	private TextField firstName;

	@FXML
	private TextField lastName;

	@FXML
	private TextField month;

	@FXML
	private TextField day;

	@FXML
	private TextField year;

	@FXML
	private TextField balance;

	@FXML
	private Button openAccount;

	@FXML
	private Button closeAccount;

	@FXML
	private Button clearFields;

	@FXML
	private Button clearOutput;

	@FXML
	private TextArea messageArea;

	// Deposit/Withdraw Funds

	@FXML
	private RadioButton checking1;

	@FXML
	private RadioButton savings1;

	@FXML
	private RadioButton moneyMarket1;

	@FXML
	private TextField firstName1;

	@FXML
	private TextField lastName1;

	@FXML
	private TextField amountChanged;

	@FXML
	private Button deposit;

	@FXML
	private Button withdraw;

	@FXML
	private ToggleGroup accountType1;

	@FXML
	private Button printAllBtn;

	@FXML
	private Button openDateBtn;

	@FXML
	private Button sortLastNameBtn;

	@FXML
	private Button resetBtn;

	@FXML
	private Button importBtn;

	@FXML
	private Button exportBtn;

	/**
	 * Initializes the program, sets isLoyal to disabled as Checking is default and
	 * creates an instance of AccountDatabase
	 */
	@FXML
	void initialize() {

		try {
			isLoyal.setDisable(true);

			accounts = new AccountDatabase();
		}

		catch (Exception e) {
			messageArea.appendText("Error occurred initializing\n");
			return;
		}
	}

	/**
	 * Checking account is selected, disabled other account type settings
	 */
	@FXML
	void checkingSelected(MouseEvent mouseEvent) {
		try {
			directDeposit.setDisable(false);
			isLoyal.setDisable(true);

			if (isLoyal.isSelected()) {
				isLoyal.setSelected(false);
			}
		}

		catch (Exception e) {
			messageArea.appendText("Error occurred selecting Checking\n");
			return;
		}
	}

	/**
	 * Savings account is selected, disabled other account type settings
	 */

	@FXML
	void savingsSelected(MouseEvent mouseEvent) {
		try {
			directDeposit.setDisable(true);
			isLoyal.setDisable(false);

			if (directDeposit.isSelected()) {
				directDeposit.setSelected(false);
			}

		}

		catch (Exception e) {
			messageArea.appendText("Error occurred selecting Savings\n");
			return;
		}

	}

	/**
	 * MoneyMarket account is selected, disabled other account type settings
	 */
	@FXML
	void moneyMarketSelected(MouseEvent mouseEvent) {

		try {
			directDeposit.setDisable(true);
			isLoyal.setDisable(true);

			if (isLoyal.isSelected()) {
				isLoyal.setSelected(false);
			}

			if (directDeposit.isSelected()) {
				directDeposit.setSelected(false);
			}
		}

		catch (Exception e) {
			messageArea.appendText("Error occurred selecting MoneyMarket\n");
			return;
		}
	}

	/**
	 * Remove account from the database
	 */
	@FXML
	void removeAccount(ActionEvent actionEvent) {

		try {
			Profile holder = checkProfile(actionEvent);

			if (holder == null) {
				return;
			}
			// System.out.println("Remove account called");
			Account account;

			RadioButton selectedAccountType = (RadioButton) accountType.getSelectedToggle();
			String selectedAccountValue = selectedAccountType.getText();

			if (selectedAccountValue.equals("Checking")) {
				boolean applyDirectDeposit = false;

				if (directDeposit.isSelected()) {
					applyDirectDeposit = true;
				}
				account = new Checking(holder, 0, null, applyDirectDeposit);
			}

			else if (accountType.getSelectedToggle().equals(savings)) {
				boolean applyIsLoyal = false;

				if (isLoyal.isSelected()) {
					applyIsLoyal = true;
				}
				account = new Savings(holder, 0, null, applyIsLoyal);

			}

			else {
				account = new MoneyMarket(holder, 0, null);
			}

			if (accounts.remove(account)) {
				messageArea.appendText("Account removed.\n");
				return;
			}

			messageArea.appendText("Account not found.\n");

		}

		catch (Exception e) {
			messageArea.appendText("Error occurred removing account\n");
			return;
		}

	}

	/**
	 * Check the date for entry to be added
	 * 
	 */
	@FXML
	Date checkDate(ActionEvent actionEvent) {

		try {
			int monthInput = checkMonth(actionEvent);
			int dayInput = checkDay(actionEvent);
			int yearInput = checkYear(actionEvent);

			Date date = new Date(monthInput, dayInput, yearInput);

			if (!date.isValid()) {
				messageArea.appendText("Date is not valid.\n");
				return null;
			}

			return new Date(monthInput, dayInput, yearInput);
		}

		catch (Exception e) {
			messageArea.appendText("Error occurred checking date\n");
			return null;
		}

	}

	/**
	 * Check the profile for entry to be added, must be greater than 0 in length for both names
	 * 
	 */
	@FXML
	Profile checkProfile(ActionEvent actionEvent) {

		try {
			if (firstName.getText().length() == 0 || lastName.getText().length() == 0) {
				messageArea.appendText("First and last name is required.\n");
				return null;
			}
			return new Profile(firstName.getText().trim(), lastName.getText().trim());

		}

		catch (NullPointerException e) {
			messageArea.appendText("First and last name is required. Please use a proper format.\n");
			return null;
		}

	}

	/**
	 * Check the profile (for the deposit/withdraw tab) for entry to be added, must be greater than 0 in length for both names
	 * 
	 */
	@FXML
	Profile checkProfileToChange(ActionEvent actionEvent) {

		try {
			if (firstName1.getText().length() == 0 || lastName1.getText().length() == 0) {
				messageArea.appendText("First and last name is required.\n");
				return null;
			}
			return new Profile(firstName1.getText().trim(), lastName1.getText().trim());

		}

		catch (NullPointerException e) {
			messageArea.appendText("First and last name is required. Please use a proper format.\n");
			return null;
		}

	}

	/**
	 * Check the balance for entry to be added, must be a double
	 * 
	 */
	@FXML
	Double checkBalance(ActionEvent actionEvent) {
		try {
			Double amount = Double.parseDouble(balance.getText());

			return amount;

		}
		// Show the error message in the TextArea.
		catch (NumberFormatException e) {
			messageArea.appendText("Balance must be a double, cannot contain letters or symbols.\n");
			return null;
		}
	}

	/**
	 * Check the amount changed for entry to be added, must be greater than 0 in length for both names
	 * 
	 */
	@FXML
	Double checkAmountChanged(ActionEvent actionEvent) {
		try {
			Double amount = Double.parseDouble(amountChanged.getText());

			return amount;

		}
		// Show the error message in the TextArea.
		catch (NumberFormatException e) {
			messageArea.appendText("Amount to add/withdraw must be a double, cannot contain letters or symbols.\n");
			return null;
		}
	}

	/**
	 * Check the inputs to create a new account, each must be valid or an account will not be created
	 * 
	 */
	@FXML
	Account checkInputs(ActionEvent actionEvent) {

		try {
			Date date = checkDate(actionEvent);

			Profile holder = checkProfile(actionEvent);

			Double amount = checkBalance(actionEvent);

			if (date == null || holder == null || amount == null) {
				return null;
			}

			Account account = null;

			RadioButton selectedAccountType = (RadioButton) accountType.getSelectedToggle();
			String selectedAccountValue = selectedAccountType.getText();

			if (selectedAccountValue.equals("Checking")) {
				boolean applyDirectDeposit = false;

				if (directDeposit.isSelected()) {
					applyDirectDeposit = true;
				}
				account = new Checking(holder, amount, date, applyDirectDeposit);
			}

			else if (accountType.getSelectedToggle().equals(savings)) {
				boolean applyIsLoyal = false;

				if (isLoyal.isSelected()) {
					applyIsLoyal = true;
				}
				account = new Savings(holder, amount, date, applyIsLoyal);

			}

			else {
				account = new MoneyMarket(holder, amount, date);
			}

			return account;
		}

		catch (Exception e) {
			messageArea.appendText("Error occurred checking inputs\n");
			return null;
		}

	}

	/**
	 * Add an account to the database
	 * 
	 */
	@FXML
	void addAccount(ActionEvent actionEvent) {

		try {
			Account account = checkInputs(actionEvent);

			if (account == null) {
				return;
			}

			boolean accountWasAdded = accounts.add(account);

			if (!accountWasAdded) {
				messageArea.appendText("Account already exists\n");
				return;
			}

			messageArea.appendText("Account successfully created!\n");
			messageArea.appendText(account.toString() + "\n");
		}

		catch (Exception e) {
			messageArea.appendText("Error occurred adding account\n");
			return;
		}

	}
	
	/**
	 * Check the month value for date, must be an integer between 1 and 12 (inclusive)
	 * 
	 */

	@FXML
	int checkMonth(ActionEvent actionEvent) {
		try {
			int monthAsInt = Integer.parseInt(month.getText());

			if (monthAsInt < 1 || monthAsInt > 12) {
				messageArea.appendText("Month must be an integer between 1 and 12 (Inclusive).\n");
				return -1;
			}

			return monthAsInt;

		}
		// Show the error message in the TextArea.
		catch (NumberFormatException e) {
			messageArea.appendText("Month must be an integer between 1 and 12 (Inclusive).\n");
			return -1;
		}
	}
	
	/**
	 *  Check the day value for date, must be an integer between 1 and 31 (inclusive)
	 * 
	 */

	@FXML
	int checkDay(ActionEvent actionEvent) {
		try {
			int dayAsInt = Integer.parseInt(day.getText());

			if (dayAsInt < 1 || dayAsInt > 31) {
				messageArea.appendText("Day must be an integer between 1 and 31 (Inclusive).\n");
				return -1;
			}

			return dayAsInt;

		}
		// Show the error message in the TextArea.
		catch (NumberFormatException e) {
			messageArea.appendText("Day must be an integer between 1 and 31 (Inclusive).\n");
			return -1;
		}
	}
	
	/**
	 *  Check the year value for date, must be an integer between 2000 and 2100 (inclusive)
	 * 
	 */
	@FXML
	int checkYear(ActionEvent actionEvent) {
		try {
			int yearAsInt = Integer.parseInt(year.getText());

			if (yearAsInt < 2000 || yearAsInt > 2100) {
				messageArea.appendText("Year must be an integer between 2000 and 2100 (Inclusive).\n");
				return -1;
			}

			return yearAsInt;

		}
		// Show the error message in the TextArea.
		catch (NumberFormatException e) {
			messageArea.appendText("Year must be an integer between 2000 and 2100 (Inclusive).\n");
			return -1;
		}
	}

	/**
	 *  Add credit to an account
	 * 
	 */
	@FXML
	void addCredit(ActionEvent actionEvent) {
		try {
			Profile profile = checkProfileToChange(actionEvent);
			Double amount = checkAmountChanged(actionEvent);

			if (profile == null || amount == null) {
				messageArea.appendText("Name and amount must be valid to add/withdraw");
				return;
			}

			Account account;

			RadioButton selectedAccountType = (RadioButton) accountType1.getSelectedToggle();
			String selectedAccountValue = selectedAccountType.getText();

			// messageArea.appendText(selectedAccountValue);
			if (selectedAccountValue.equals("Checking")) {
				boolean applyDirectDeposit = false;

				if (directDeposit.isSelected()) {
					applyDirectDeposit = true;
				}
				account = new Checking(profile, 0, new Date(1, 1, 2000), applyDirectDeposit);
			}

			else if (selectedAccountValue.equals("Savings")) {
				boolean applyIsLoyal = false;

				if (isLoyal.isSelected()) {
					applyIsLoyal = true;
				}
				account = new Savings(profile, 0, new Date(1, 1, 2000), applyIsLoyal);

			}

			else {
				account = new MoneyMarket(profile, 0, new Date(1, 1, 2000));
			}

			int accountIndex = accounts.getAccountIndex(account);
			Account[] accountArray = accounts.getAccounts();

			if (!accounts.deposit(accountArray[accountIndex], amount)) {
				messageArea.appendText("Account not found, cannot deposit or withdraw\n");
				return;
			}

			messageArea.appendText("$" + String.format("%.2f", amount) + " added to " + account.getAccountType() + " "
					+ account.getHolder().toString() + "\n");
		}

		catch (Exception e) {
			messageArea.appendText("Error occurred depositing\n");
			return;
		}

	}

	/**
	 *  Withdraw money from an account
	 * 
	 */
	@FXML
	void withdraw(ActionEvent actionEvent) {
		try {

			Profile profile = checkProfileToChange(actionEvent);
			Double amount = checkAmountChanged(actionEvent);

			if (profile == null || amount == null) {
				messageArea.appendText("Name and amount must be valid to add/withdraw");
				return;
			}

			Account account;

			RadioButton selectedAccountType = (RadioButton) accountType1.getSelectedToggle();
			String selectedAccountValue = selectedAccountType.getText();

			if (selectedAccountValue.equals("Checking")) {
				boolean applyDirectDeposit = false;

				if (directDeposit.isSelected()) {
					applyDirectDeposit = true;
				}
				account = new Checking(profile, 0, new Date(1, 1, 2000), applyDirectDeposit);
			}

			else if (selectedAccountValue.equals("Savings")) {
				boolean applyIsLoyal = false;

				if (isLoyal.isSelected()) {
					applyIsLoyal = true;
				}
				account = new Savings(profile, 0, new Date(1, 1, 2000), applyIsLoyal);

			}

			else {
				account = new MoneyMarket(profile, 0, new Date(1, 1, 2000));
			}

			int accountIndex = accounts.getAccountIndex(account);
			Account[] accountArray = accounts.getAccounts();

			int withdrawalResult = accounts.withdrawal(accountArray[accountIndex], amount);
			if (withdrawalResult == -1) {
				messageArea.appendText("Account not found, cannot deposit or withdraw\n");
				return;
			}

			else if (withdrawalResult == 1) {
				messageArea.appendText("Insufficient funds, cannot withdraw\n");
				return;
			}

			messageArea.appendText("$" + String.format("%.2f", amount) + " withdrawn from " + account.getAccountType()
					+ " " + account.getHolder().toString() + "\n");
		}

		catch (Exception e) {
			messageArea.appendText("Error occurred withdrawing/Account not found\n");
			return;
		}

	}
	
	/**
	 *  Print all accounts in the database
	 */

	@FXML
	void printAllAccounts(ActionEvent actionEvent) {
		try {
			messageArea.appendText(accounts.printAccountsToTextArea());

		}

		catch (Exception e) {
			messageArea.appendText("Error printing all accounts");
			return;
		}
	}

	/**
	 *  Print all accounts in the database sorted by open date
	 */
	@FXML
	void printByOpenDate(ActionEvent actionEvent) {
		try {
			messageArea.appendText(accounts.printByDateOpenToTextArea());

		}

		catch (Exception e) {
			messageArea.appendText("Error sorting by open date");
			return;
		}
	}

	/**
	 *  Print all accounts in the database sorted by last name
	 */
	@FXML
	void printByLastName(ActionEvent actionEvent) {
		try {
			messageArea.appendText(accounts.printByLastNameToTextArea());

		}

		catch (Exception e) {
			messageArea.appendText("Error sorting by last name");
			return;
		}
	}

	
	/**
	 *  Clear all input in the open/close account tab
	 */
	@FXML
	void clearInput(ActionEvent actionEvent) {
		try {
			firstName.clear();
			lastName.clear();
			month.clear();
			day.clear();
			year.clear();
			balance.clear();
		}

		catch (Exception e) {
			messageArea.appendText("Error clearing input");
			return;
		}
	}

	
	/**
	 *  Clear all output from the text area
	 */
	@FXML
	void clearOutput(ActionEvent actionEvent) {
		try {
			messageArea.clear();

		}

		catch (Exception e) {
			messageArea.appendText("Error clearing output");
			return;
		}
	}

	/**
	 *  Import an external text file to write into the database
	 */
	@FXML
	void importFile(ActionEvent event) {
		try {
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Open Source File for the Import");
			chooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
					new ExtensionFilter("All Files", "*.*"));
			Stage stage = new Stage();
			File sourceFile = chooser.showOpenDialog(stage);
			Account account;
			BufferedReader reader;
			String[] accountProperties;

			reader = new BufferedReader(new FileReader(sourceFile));
			String line = reader.readLine();
			while (line != null) {
				accountProperties = line.split(",");

				account = checkFileInput(accountProperties);
				if (account == null) {
					line = reader.readLine();
				}

				else {

					accounts.add(account);
					messageArea.appendText(line + "\n");
					// read next line
					line = reader.readLine();
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();

			messageArea
					.appendText("File contains improper input format, please check each entry to ensure correctness\n");
			return;
		}
	} 

	
	/**
	 *  Check the file input to ensure that an account can be created given the file inputs
	 */
	Account checkFileInput(String[] account) {
		try {
			String accountType = account[0].trim();
			String firstName = account[1].trim();
			String lastName = account[2].trim();
			String balance = account[3].trim();
			String[] date = account[4].trim().split("/");
			String condition = account[5].trim();

			// messageArea.appendText(Arrays.toString(account));

			if (firstName.length() == 0 || lastName.length() == 0) {
				messageArea.appendText("First and last name is required\n");

				return null;
			}

			if (!accountType.equals("S") && !accountType.equals("C") && !accountType.equals("M")) {
				messageArea.appendText("Account type is not valid\n");
				return null;
			}

			Double validBalance = checkFileBalance(balance);

			if (validBalance == null) {
				messageArea.appendText("Balance is not valid\n");
				return null;
			}

			Date d = checkFileDate(date);

			if (!d.isValid()) {
				messageArea.appendText("Date is not valid\n");

				return null;
			}

			if (!checkFileCondition(accountType, condition)) {
				return null;
			}

			Profile profile = new Profile(firstName, lastName);

			Account accountToAdd = null;

			if (accountType.equals("S")) {
				accountToAdd = new Savings(profile, validBalance, d, Boolean.parseBoolean(condition));
				return accountToAdd;
			}

			else if (accountType.equals("C")) {
				accountToAdd = new Checking(profile, validBalance, d, Boolean.parseBoolean(condition));
				return accountToAdd;

			}

			else {
				accountToAdd = new MoneyMarket(profile, validBalance, d, Integer.parseInt(condition));
				return accountToAdd;

			}

		}

		catch (Exception e) {
			messageArea
					.appendText("File contains improper input format, please check each entry to ensure correctness\n");
			return null;
		}
	}

	/**
	 *  Check to make sure that account types match their special conditions, e.g. MoneyMarket will include an integer for withdrawals and the others will be true or false
	 */
	boolean checkFileCondition(String accountType, String condition) {
		try {

			if (accountType.equals("M")) {
				int withdrawals = Integer.parseInt(condition);
				return true;
			}

			if (!condition.equals("true") && !condition.equals("false")) {
				messageArea.appendText("Invalid account condition, must be true or false\n");
				return false;
			}

			return true;
		}

		catch (Exception e) {
			messageArea.appendText("Invalid account condition, must be integer if M, or true or false if S or C\n");

			return false;

		}

	}

	/**
	 *  Check the balance given by the import file, must be a double
	 */
	Double checkFileBalance(String balance) {
		try {
			Double amount = Double.parseDouble(balance);

			return amount;

		}
		// Show the error message in the TextArea.
		catch (NumberFormatException e) {
			messageArea.appendText("Balance must be a double, cannot contain letters or symbols.\n");
			return null;
		}
	}

	
	/**
	 *  Check the date given by the import file, must be a valid date format
	 */
	Date checkFileDate(String[] date) {
		try {
			int monthAsInt = Integer.parseInt(date[0]);

			if (monthAsInt < 1 || monthAsInt > 12) {
				messageArea.appendText("Month must be an integer between 1 and 12 (Inclusive).\n");
				return null;
			}

			int dayAsInt = Integer.parseInt(date[1]);

			if (dayAsInt < 1 || dayAsInt > 31) {
				messageArea.appendText("Day must be an integer between 1 and 31 (Inclusive).\n");
				return null;
			}

			int yearAsInt = Integer.parseInt(date[2]);

			if (yearAsInt < 2000 || yearAsInt > 2100) {
				messageArea.appendText("Year must be an integer between 2000 and 2100 (Inclusive).\n");
				return null;
			}

			Date d = new Date(monthAsInt, dayAsInt, yearAsInt);

			return d;

		}
		// Show the error message in the TextArea.
		catch (Exception e) {
			messageArea.appendText("Date values must valid\n");
			return null;
		}
	}
	
	/**
	 *  Export all accounts in the database to a text file
	 */

	@FXML
	void exportFile(ActionEvent actionEvent) {
		try {

			String text = "";
			Account[] acc = accounts.getAccounts();

			for (int i = 0; i < accounts.getSize(); i++) {
				text += acc[i].getAccountLabel() + "," + acc[i].getHolder().getFirstName() + ","
						+ acc[i].getHolder().getLastName() + "," + String.format("%.2f", acc[i].getBalance()) + ","
						+ acc[i].getOpenDate() + acc[i].getConditionToFile() + "\n";
			}
			FileChooser fileChooser = new FileChooser();
			Stage stage = new Stage();

			// Set extension filter for text files
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
			fileChooser.getExtensionFilters().add(extFilter);

			// Show save file dialog
			File file = fileChooser.showSaveDialog(stage);

			if (file != null) {
				saveTextToFile(text, file);
			}
		}

		catch (Exception e) {
			messageArea.appendText("Error occurred exporting file\n");
			return;
		}
	}

	/**
	 *  Save the text of accounts to a file to export
	 */
	public void saveTextToFile(String text, File file) {
		try {
			PrintWriter writer;
			writer = new PrintWriter(file);
			writer.println(text);
			writer.close();
		} catch (Exception e) {
			messageArea.appendText(text);
			return;
		}
	}
	
	/**
	 * Reset the account database, all accounts will be removed.
	 */

	@FXML
	void resetDatabase(ActionEvent actionEvent) {
		try {
			accounts = new AccountDatabase();
			messageArea.appendText("Database has been reset\n");

		}

		catch (Exception e) {
			messageArea.appendText("Error occurred resetting database\n");
			return;
		}
	}
}
