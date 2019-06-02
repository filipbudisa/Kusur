package com.filipbudisa.kusur.view;

import com.filipbudisa.kusur.model.Transaction;

public class TransactionView {
	private long id;

	private String type;

	private double amount;

	private IncomeView income;

	private ExpenseView expense;

	public TransactionView(Transaction transaction){
		this.id = transaction.getId();
		this.type = transaction.getType().toString().toLowerCase();
		this.amount = transaction.getAmount();
		this.income = new IncomeView(transaction.getIncome());
		this.expense = new ExpenseView(transaction.getExpense());
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

	public IncomeView getIncome(){
		return income;
	}

	public ExpenseView getExpense(){
		return expense;
	}
}
