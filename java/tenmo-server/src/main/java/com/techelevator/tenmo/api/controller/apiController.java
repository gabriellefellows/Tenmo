package com.techelevator.tenmo.api.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.techelevator.tenmo.accounts.JDBCaccountsDAO;
import com.techelevator.tenmo.accounts.accounts;

@RestController
public class apiController {
	private JDBCaccountsDAO jdbcaccounts;
	
	public apiController(JDBCaccountsDAO jdbcaccounts) {
		this.jdbcaccounts = jdbcaccounts;
	}
	
	
	@RequestMapping(path="/accounts/{user_id}", method=RequestMethod.GET)
	public accounts getAcctBalance(@PathVariable int user_id) {
		
		return jdbcaccounts.getAcctBalanceByID(user_id);
	}
}
