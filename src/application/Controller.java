package application;

import java.io.File;
import java.text.DecimalFormat;

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


public class Controller {
	
	static AccountDatabase accounts;
	
	private int characterLimit = 30;
	
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
	private TextArea messageArea;
	
	@FXML
	void initialize() {
		isLoyal.setDisable(true);
		
		accounts = new AccountDatabase();
		
	}
	
	@FXML
	void checkingSelected(MouseEvent mouseEvent) {
			directDeposit.setDisable(false);
			isLoyal.setDisable(true);
			
			if(isLoyal.isSelected()) {
				isLoyal.setSelected(false);
			}

	}
	
	@FXML
	void savingsSelected(MouseEvent mouseEvent) {
			directDeposit.setDisable(true);
			isLoyal.setDisable(false);

			if(directDeposit.isSelected()) {
				directDeposit.setSelected(false);
			}

	}
	
	@FXML
	void moneyMarketSelected(MouseEvent mouseEvent) {
		directDeposit.setDisable(true);
		isLoyal.setDisable(true);
		
		if(isLoyal.isSelected()) {
			isLoyal.setSelected(false);
		}
		
		if(directDeposit.isSelected()) {
			directDeposit.setSelected(false);
		}
	}
	
	@FXML
	void removeAccount(ActionEvent actionEvent) {
		
		Profile holder = checkProfile(actionEvent);
		
		if(holder == null) {
			return;
		}
		//System.out.println("Remove account called");
		Account account;
		
		RadioButton selectedAccountType = (RadioButton) accountType.getSelectedToggle();
		String selectedAccountValue = selectedAccountType.getText();
		
		if(selectedAccountValue.equals("Checking")) {
			boolean applyDirectDeposit = false;
			
			if(directDeposit.isSelected()) {
				applyDirectDeposit = true;
			}
			account = new Checking(holder, 0, null, applyDirectDeposit);
		}
		
		else if(accountType.getSelectedToggle().equals(savings)) {
			boolean applyIsLoyal = false;
			
			if(isLoyal.isSelected()) {
				applyIsLoyal = true;
			}
			account = new Savings(holder, 0, null, applyIsLoyal);

		}
		
		else {
			account = new MoneyMarket(holder, 0, null);
		}
		
		if(accounts.remove(account)) {
			messageArea.appendText("Account removed.\n");
			return;
		}
		
		messageArea.appendText("Account not found.\n");

	}
	
	@FXML
	Date checkDate(ActionEvent actionEvent) {
		int monthInput = checkMonth(actionEvent);
		int dayInput = checkDay(actionEvent);
		int yearInput = checkYear(actionEvent);
		
		Date date = new Date(monthInput, dayInput, yearInput);
		
		if(!date.isValid()) {
			messageArea.appendText("Date is not valid.\n");
			return null;
		}
		
		return new Date(monthInput, dayInput, yearInput);
	}
	
	@FXML
	Profile checkProfile(ActionEvent actionEvent) {
		
		try {
			if(firstName.getText().length() == 0 || lastName.getText().length() == 0) {
	    		messageArea.appendText("First and last name is required.\n");
	    		return null;
			}
			return new Profile(firstName.getText().trim(), lastName.getText().trim());

		}
		
		catch(NullPointerException e) {
    		messageArea.appendText("First and last name is required. Please use a proper format.\n");
    		return null;
		}
		
	}
	
	@FXML
	Double checkBalance(ActionEvent actionEvent) {
		try {
    		Double amount = Double.parseDouble(balance.getText()); 
    		
    		return amount;
    		
    	}
    	//Show the error message in the TextArea.
    	catch (NumberFormatException e) {
    		messageArea.appendText("Balance must be a double, cannot contain letters or symbols.\n");
    		return null;
    	}
	}
	
	
	
	@FXML
	void checkInputs(ActionEvent actionEvent) {
		
		Date date = checkDate(actionEvent);
		
		Profile holder = checkProfile(actionEvent);
		
		Double amount = checkBalance(actionEvent);
		
		if(date == null || holder == null || amount == null) {
			return;
		}
		
		Account account;
		
		//DecimalFormat df = new DecimalFormat("#.00");
		//df.format(amount);
		
		
		
		RadioButton selectedAccountType = (RadioButton) accountType.getSelectedToggle();
		String selectedAccountValue = selectedAccountType.getText();
		
		if(selectedAccountValue.equals("Checking")) {
			boolean applyDirectDeposit = false;
			
			if(directDeposit.isSelected()) {
				applyDirectDeposit = true;
			}
			account = new Checking(holder, amount, date, applyDirectDeposit);
		}
		
		else if(accountType.getSelectedToggle().equals(savings)) {
			boolean applyIsLoyal = false;
			
			if(isLoyal.isSelected()) {
				applyIsLoyal = true;
			}
			account = new Savings(holder, amount, date, applyIsLoyal);

		}
		
		else {
			account = new MoneyMarket(holder, amount, date);
		}
		
		boolean accountWasAdded = accounts.add(account);
		
		if(!accountWasAdded) {
			messageArea.appendText("Account already exists\n");
			return;
		}
		
		messageArea.appendText("Account successfully created!\n");
		messageArea.appendText(account.toString() + "\n");
		
		
	}
	
	
	
	@FXML
	int checkMonth(ActionEvent actionEvent) {
		try {
    		int monthAsInt = Integer.parseInt(month.getText()); 
    		
    		if(monthAsInt < 1 || monthAsInt > 12) {
    			messageArea.appendText("Month must be an integer between 1 and 12 (Inclusive).\n");
        		return -1;
    		}
    		
    		return monthAsInt;
    		
    	}
    	//Show the error message in the TextArea.
    	catch (NumberFormatException e) {
    		messageArea.appendText("Month must be an integer between 1 and 12 (Inclusive).\n");
    		return -1;
    	}
	}
	
	@FXML
	int checkDay(ActionEvent actionEvent) {
		try {
    		int dayAsInt = Integer.parseInt(day.getText()); 
    		
    		if(dayAsInt < 1 || dayAsInt > 31) {
    			messageArea.appendText("Day must be an integer between 1 and 31 (Inclusive).\n");
        		return -1;
    		}
    		
    		return dayAsInt;
    		
    	}
    	//Show the error message in the TextArea.
    	catch (NumberFormatException e) {
    		messageArea.appendText("Day must be an integer between 1 and 31 (Inclusive).\n");
    		return -1;
    	}
	}
	
	@FXML
	int checkYear(ActionEvent actionEvent) {
		try {
    		int yearAsInt = Integer.parseInt(year.getText()); 
    		
    		if(yearAsInt < 2000 || yearAsInt > 2100) {
    			messageArea.appendText("Year must be an integer between 2000 and 2100 (Inclusive).\n");
        		return -1;
    		}
    		
    		return yearAsInt;
    		
    	}
    	//Show the error message in the TextArea.
    	catch (NumberFormatException e) {
    		messageArea.appendText("Year must be an integer between 2000 and 2100 (Inclusive).\n");
    		return -1;
    	}
	}
	
	
	@FXML
	void clearInput(ActionEvent actionEvent) {
		firstName.clear();
		lastName.clear();
		month.clear();
		day.clear();
		year.clear();
		balance.clear();
	}
	


	
	
	
}
