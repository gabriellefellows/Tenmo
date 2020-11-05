package com.techelevator.tenmo.transfers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.accounts.accounts;
import com.techelevator.tenmo.accounts.accountsDAO;

@Component
public class JDBCtransfersDAO implements transfersDAO{

private JdbcTemplate jdbcTemplate;
private accountsDAO accountDAO;

public JDBCtransfersDAO(DataSource dataSource) {
	this.jdbcTemplate = new JdbcTemplate(dataSource);
}

public List<transfers> viewTransactions() {
	List<transfers> transferList = new ArrayList<>();
	String sql = "SELECT * FROM transfers"
			+ " WHERE account_from = accounts.user_id OR account_to = accounts.user_id";
			//+ " JOIN accounts a ON a.account_id = t.account_to"
			//+ " JOIN users u ON a.user_id = u.user_id";
			//+ " WHERE u.user_id = ? OR a.user_id = ?";
			
	SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while(results.next()) {
			transfers transfer = mapRowToTransfers(results);
			transferList.add(transfer);
		}
		return transferList;
}

private transfers mapRowToTransfers(SqlRowSet results) {
	transfers transfer = new transfers();
	transfer.setTransfer_id(results.getInt("transfer_id"));
	transfer.setTransfer_type_id(results.getInt("transfer_type_id"));
	transfer.setTransfer_status_id(results.getInt("transnfer_status_id"));
	transfer.setAccount_from(results.getInt("account_from"));
	transfer.setAccount_to(results.getInt("account_to"));
	transfer.setAmount(results.getBigDecimal("amount"));
	return transfer;
}


}
