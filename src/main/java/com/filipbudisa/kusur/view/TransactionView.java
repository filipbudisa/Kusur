package com.filipbudisa.kusur.view;

import com.filipbudisa.kusur.model.Transaction;

public class TransactionView {
	protected long id;

	protected String type;

	protected double amount;

	public TransactionView(Transaction transaction){
		this.id = transaction.getId();
		this.amount = transaction.getAmount();

		this.type = transaction.getClass().getSimpleName().toLowerCase();
	}

	public long getId(){
		return id;
	}

	public String getType(){
		return type;
	}

	public double getAmount(){
		return amount;
	}
}
