package com.techelevator.tenmo.transfers;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.techelevator.tenmo.accounts.accounts;
import com.techelevator.tenmo.accounts.accountsDAO;

@Component
public class JDBCtransfersDAO implements transfersDAO {

	private JdbcTemplate jdbcTemplate;
	private accountsDAO accountDAO;

	public JDBCtransfersDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<transfers> viewTransactionsByFromID(int user_id) {
		transfers transfer = null;
		List<transfers> transferList = new ArrayList<>();
		String sql = "SELECT * FROM transfers"
				+ " WHERE account_from = ?";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user_id);
		while (results.next()) {
			transfer = mapRowToTransfers(results);
			transferList.add(transfer);
		}
		return transferList;
	}
	
	public List<transfers> viewTransactionsByToID(int user_id) {
		transfers transfer = null;
		List<transfers> transferList = new ArrayList<>();
		String sql = "SELECT * FROM transfers"
				+ " WHERE account_to = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, user_id);
		while (results.next()) {
			transfer = mapRowToTransfers(results);
			transferList.add(transfer);
		}
		System.out.println("number of rows" + transferList.size());
		return transferList;
	}
	
	public transfers viewTransferDetails(int transfer_id) {
		transfers viewTransferDetails = null;
		String sql = "SELECT * FROM transfers WHERE transfer_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transfer_id);
		while(results.next()) {
			viewTransferDetails = mapRowToTransfers(results);	
		}
		return viewTransferDetails;
	}
	
	public void updateTransactions(transfers t) {
		String sql = "INSERT INTO transfers (transfer_type_id, transfer_status_id, account_from, account_to, amount)"
				+ " VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, t.getTransfer_type_id(), t.getTransfer_status_id(), t.getAccount_from(),
				t.getAccount_to(), t.getAmount());
	}

	private transfers mapRowToTransfers(SqlRowSet results) {
		transfers transfer = new transfers();
		transfer.setTransfer_id(results.getInt("transfer_id"));
		transfer.setTransfer_type_id(results.getInt("transfer_type_id"));
		transfer.setTransfer_status_id(results.getInt("transfer_status_id"));
		transfer.setAccount_from(results.getInt("account_from"));
		transfer.setAccount_to(results.getInt("account_to"));
		transfer.setAmount(results.getBigDecimal("amount"));
		return transfer;
	}

}
