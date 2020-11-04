package com.techelevator.tenmo.accounts;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JDBCaccountsDAO implements accountsDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCaccountsDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public accounts getAcctBalance() {
		String sqlGetAcctBalance = "SELECT balance FROM accounts";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAcctBalance);
		
		accounts accountBalance = mapRowToAccounts(results);
		return accountBalance.getBalance();
	}

	@Override
	public accounts sendMoney() {
		// TODO Auto-generated method stub
		return null;
	}


	private accounts mapRowToAccounts(SqlRowSet results) {
		accounts account = new accounts();
		account.setAccount_id(results.getInt("account_id"));
		account.setUser_id(results.getInt("user_id"));
		account.setBalance(results.getDouble("balance"));
		return account;
		
	}
	
	
}
