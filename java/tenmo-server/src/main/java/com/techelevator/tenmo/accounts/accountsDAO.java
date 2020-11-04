package com.techelevator.tenmo.accounts;

public interface accountsDAO {
	
	//method to see acct balance
	public accounts getAcctBalanceByID(int user_id);
	
	
	//method to send money
	public accounts sendMoney();
	
	
	
	
}
