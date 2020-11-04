package com.techelevator.tenmo.transfers;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.accounts.accounts;

@Component
public class JDBCtransfersDAO implements transfersDAO{

private JdbcTemplate jdbcTemplate;

public JDBCtransfersDAO(DataSource dataSource) {
	this.jdbcTemplate = new JdbcTemplate(dataSource);
}

@Override
public transfers seeSentAndReceived() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public transfers seeTransferDetails() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public transfers sendMoney() {
	
	return null;
}

private transfers mapRowToTransfers(SqlRowSet results) {
	transfers transfer = new transfers();
	transfer.setTransfer_id(results.getInt("transfer_id"));
	transfer.setTransfer_type_id(results.getInt("transfer_type_id"));
	transfer.setTransfer_status_id(results.getInt("transnfer_status_id"));
	transfer.setAccount_from(results.getInt("account_from"));
	transfer.setAccount_to(results.getInt("account_to"));
	transfer.setAmount(results.getDouble("amount"));
	return transfer;
}

}
