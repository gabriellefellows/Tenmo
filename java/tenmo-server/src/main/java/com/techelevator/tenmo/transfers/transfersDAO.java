package com.techelevator.tenmo.transfers;

public interface transfersDAO {

	
	public transfers sendMoney();
	//method to see transfers sent and received
	public transfers seeSentAndReceived();
	//method to retrieve details of transfer based on id
	public transfers seeTransferDetails();
	
	
	
}
