package com.techelevator.tenmo.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.techelevator.tenmo.accounts.JDBCaccountsDAO;
import com.techelevator.tenmo.accounts.accounts;
import com.techelevator.tenmo.dao.UserSqlDAO;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.transfers.JDBCtransfersDAO;
import com.techelevator.tenmo.transfers.transfers;

@RestController
public class apiController {
	private JDBCaccountsDAO jdbcaccounts;
	private UserSqlDAO jdbcUser;
	private JDBCtransfersDAO jdbcTransfer;
	
	public apiController(JDBCaccountsDAO jdbcaccounts, UserSqlDAO jdbcUser, JDBCtransfersDAO jdbcTransfer) {
		this.jdbcaccounts = jdbcaccounts;
		this.jdbcUser = jdbcUser;
		this.jdbcTransfer = jdbcTransfer;
	}
	
	@RequestMapping(path="/accounts/{user_id}", method=RequestMethod.GET)
	public accounts getAcctByID(@PathVariable int user_id) {
		return jdbcaccounts.getAcctByID(user_id);
	}

	@RequestMapping(path="/users", method=RequestMethod.GET)
	public List<User> getAllUsers() {
		return jdbcUser.findAll();
	
	}
	
	@RequestMapping(path="/accounts/{user_id}", method=RequestMethod.PUT)
	public void updateAcctBalance(@PathVariable int user_id, @RequestBody accounts account) {
		jdbcaccounts.updateAcctBalance(account);
	}

	@RequestMapping(path="/transfers", method=RequestMethod.GET)
	public List<transfers> listAllTransfers(){
		return jdbcTransfer.viewTransactions();
	}
	
}
