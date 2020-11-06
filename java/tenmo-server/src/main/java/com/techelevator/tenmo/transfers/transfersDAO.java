package com.techelevator.tenmo.transfers;

import java.util.List;

public interface transfersDAO {


	public List<transfers> viewTransactionsByFromID(int user_id);
	
	public void updateTransactions(transfers t);
	
	public List<transfers> viewTransactionsByToID(int user_id);
	
	public transfers viewTransferDetails(int transfer_id);
}
