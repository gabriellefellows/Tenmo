package com.techelevator.tenmo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.techelevator.application.models.accounts;
import com.techelevator.tenmo.models.AuthenticatedUser;
import com.techelevator.tenmo.models.User;
import com.techelevator.tenmo.models.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.AuthenticationServiceException;
import com.techelevator.view.ConsoleService;

public class App {

	private static final String API_BASE_URL = "http://localhost:8080/";

	private static final String MENU_OPTION_EXIT = "Exit";
	private static final String LOGIN_MENU_OPTION_REGISTER = "Register";
	private static final String LOGIN_MENU_OPTION_LOGIN = "Login";
	private static final String[] LOGIN_MENU_OPTIONS = { LOGIN_MENU_OPTION_REGISTER, LOGIN_MENU_OPTION_LOGIN,
			MENU_OPTION_EXIT };
	private static final String MAIN_MENU_OPTION_VIEW_BALANCE = "View your current balance";
	private static final String MAIN_MENU_OPTION_SEND_BUCKS = "Send TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS = "View your past transfers";
	private static final String MAIN_MENU_OPTION_REQUEST_BUCKS = "Request TE bucks";
	private static final String MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS = "View your pending requests";
	private static final String MAIN_MENU_OPTION_LOGIN = "Login as different user";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_VIEW_BALANCE, MAIN_MENU_OPTION_SEND_BUCKS,
			MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS, MAIN_MENU_OPTION_REQUEST_BUCKS,
			MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS, MAIN_MENU_OPTION_LOGIN, MENU_OPTION_EXIT };

	private AuthenticatedUser currentUser;
	private ConsoleService console;
	private AuthenticationService authenticationService;
	private RestTemplate apiCall = new RestTemplate();
	private Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		App app = new App(new ConsoleService(System.in, System.out), new AuthenticationService(API_BASE_URL));
		app.run();
	}

	public App(ConsoleService console, AuthenticationService authenticationService) {
		this.console = console;
		this.authenticationService = authenticationService;
	}

	public void run() {
		System.out.println("*********************");
		System.out.println("* Welcome to TEnmo! *");
		System.out.println("*********************");

		registerAndLogin();
		mainMenu();
	}

	private void mainMenu() {
		while (true) {
			String choice = (String) console.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if (MAIN_MENU_OPTION_VIEW_BALANCE.equals(choice)) {
				viewCurrentBalance();
			} else if (MAIN_MENU_OPTION_VIEW_PAST_TRANSFERS.equals(choice)) {
				viewTransferHistory();
			} else if (MAIN_MENU_OPTION_VIEW_PENDING_REQUESTS.equals(choice)) {
				viewPendingRequests();
			} else if (MAIN_MENU_OPTION_SEND_BUCKS.equals(choice)) {
				sendBucks();
			} else if (MAIN_MENU_OPTION_REQUEST_BUCKS.equals(choice)) {
				requestBucks();
			} else if (MAIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else {
				// the only other option on the main menu is to exit
				exitProgram();
			}
		}
	}

	private void viewCurrentBalance() {
		accounts account = apiCall.getForObject(API_BASE_URL + "accounts/" + currentUser.getUser().getId(),
				accounts.class);
		System.out.println("Your current balance is: " + account.getBalance());
	}

	private void viewTransferHistory() {
		// TODO Auto-generated method stub

	}

	private void viewPendingRequests() {
		// TODO Auto-generated method stub

	}

	private void sendBucks() {
		ResponseEntity<User[]> users = apiCall.getForEntity(API_BASE_URL + "users", User[].class);
		List<User> listAllUsers = Arrays.asList(users.getBody());
		System.out.println("Listing all users: ---------------------");
		if (listAllUsers.size() > 0) {
			for (User listUser : listAllUsers) {
				System.out.println(listUser.getId() + " - " + listUser.getUsername());
			}
		} else {
			System.out.println("Error");
		}
		
		System.out.println("Please enter the id of the user you want to send money to: ");
		String userChoice = scanner.nextLine();
		

		int userId = 0;
		try {
			userId = Integer.parseInt(userChoice);
		} catch (NumberFormatException ex) {
			System.out.println("Error: Enter a valid id number.");
			return;
		}
		
		if(userId == currentUser.getUser().getId()) {
			System.out.println("You cannot transfer money to yourself... try again.");
			return;
		}
		
		
		for (User user : listAllUsers) {
			if (user.getId() == userId) {
				System.out.println("Enter amount to transfer: ");
				String amountToTransfer = scanner.nextLine();
				double cashToTransfer = 0.00;

				try {
					cashToTransfer = Double.parseDouble(amountToTransfer);
					String rounding = String.format("%.2f", cashToTransfer);
					cashToTransfer = Double.parseDouble(rounding);
				} catch (NumberFormatException ex) {
					System.out.println("Error, please try again - incorrect dollar amount.");
					return;
				}
				
				accounts fromAccount = apiCall.getForObject(API_BASE_URL + "accounts/" + currentUser.getUser().getId(),
						accounts.class);
				accounts toAccount = apiCall.getForObject(API_BASE_URL + "accounts/" + user.getId(), accounts.class);
				
				if (cashToTransfer > fromAccount.getBalance()) {
					System.out.println("Sorry, not enough money in account to transfer.");
					return;
				}else if (cashToTransfer <= 0) {
					System.out.println("Sorry, you cannot transfer debt.");
					return;
				}
				
				fromAccount.setBalance(fromAccount.getBalance()-cashToTransfer);
				toAccount.setBalance(toAccount.getBalance() + cashToTransfer);
				
				apiCall.put(API_BASE_URL + "accounts/" + fromAccount.getAccount_id(), fromAccount);
				apiCall.put(API_BASE_URL + "accounts/" + toAccount.getAccount_id(), toAccount);
				
				System.out.println("Transfer status: approved.");
				System.out.println("          *******         ");
				System.out.println("Your current balance is " + fromAccount.getBalance());
			}
		}

	

	} // end of sendMoney

	private void requestBucks() {
		// TODO Auto-generated method stub

	}

	private void exitProgram() {
		System.exit(0);
	}

	private void registerAndLogin() {
		while (!isAuthenticated()) {
			String choice = (String) console.getChoiceFromOptions(LOGIN_MENU_OPTIONS);
			if (LOGIN_MENU_OPTION_LOGIN.equals(choice)) {
				login();
			} else if (LOGIN_MENU_OPTION_REGISTER.equals(choice)) {
				register();
			} else {
				// the only other option on the login menu is to exit
				exitProgram();
			}
		}
	}

	private boolean isAuthenticated() {
		return currentUser != null;
	}

	private void register() {
		System.out.println("Please register a new user account");
		boolean isRegistered = false;
		while (!isRegistered) // will keep looping until user is registered
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				authenticationService.register(credentials);
				isRegistered = true;
				System.out.println("Registration successful. You can now login.");
			} catch (AuthenticationServiceException e) {
				System.out.println("REGISTRATION ERROR: " + e.getMessage());
				System.out.println("Please attempt to register again.");
			}
		}
	}

	private void login() {
		System.out.println("Please log in");
		currentUser = null;
		while (currentUser == null) // will keep looping until user is logged in
		{
			UserCredentials credentials = collectUserCredentials();
			try {
				currentUser = authenticationService.login(credentials);
			} catch (AuthenticationServiceException e) {
				System.out.println("LOGIN ERROR: " + e.getMessage());
				System.out.println("Please attempt to login again.");
			}
		}
	}

	private UserCredentials collectUserCredentials() {
		String username = console.getUserInput("Username");
		String password = console.getUserInput("Password");
		return new UserCredentials(username, password);
	}
}
