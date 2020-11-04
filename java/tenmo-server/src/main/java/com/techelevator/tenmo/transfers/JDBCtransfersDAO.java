package com.techelevator.tenmo.transfers;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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

	

}
