package com.filipbudisa.kusur.view;

import com.filipbudisa.kusur.model.Transaction;
import com.filipbudisa.kusur.model.User;
import org.assertj.core.util.Lists;

import java.util.List;
import java.util.stream.Collectors;

public class UserView {

	private long id;

	private String name;

	private double balance;

	private List<TransactionView> transactions;

	public UserView(User user){
		this.id = user.getId();
		this.name = user.getName();
		this.balance = user.getBalance();

		this.transactions = user.getTransactions()
			.parallelStream()
			.map(TransactionView::new)
			.collect(Collectors.toList());
	}

	public long getId(){
		return id;
	}

	public String getName(){
		return name;
	}

	public double getBalance(){
		return balance;
	}

	public List<TransactionView> getTransactions(){
		return transactions;
	}
}
