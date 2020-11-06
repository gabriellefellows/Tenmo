package com.techelevator.tenmo.accounts;



import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
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
	public accounts getAcctByID(int user_id) { // use to call balance
		accounts account = null;
		String sqlGetAcctBalance = "SELECT * FROM accounts WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAcctBalance, user_id);
			
		if(results.next()) { // if a row was returned, position to it
			account = mapRowToAccounts(results); // and call map row to accounts to convert to an acct
		}
		return account;
	}
	
	public void updateAcctBalance(accounts account) {
		String sql = "UPDATE accounts SET balance =? WHERE account_id = ?";
		jdbcTemplate.update(sql, account.getBalance(), account.getAccount_id());
	}

	private accounts mapRowToAccounts(SqlRowSet results) {
		accounts account = new accounts();
		account.setAccount_id(results.getInt("account_id"));
		account.setUser_id(results.getInt("user_id"));
		account.setBalance(results.getDouble("balance"));
		return account;
		
	}
}
