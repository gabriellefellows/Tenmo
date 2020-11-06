package com.techelevator.tenmo.accounts;

import java.math.BigDecimal;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.tenmo.model.User;

public interface accountsDAO {
	
	//method to see acct balance
	public accounts getAcctByID(int user_id);
	
	public void updateAcctBalance(accounts account);
	
}
