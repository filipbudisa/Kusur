package com.filipbudisa.kusur.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	private String name;

	private double balance;

	@OneToMany(mappedBy = "from", fetch = FetchType.LAZY)
	private List<Transfer> sentTransfers;

	@OneToMany(mappedBy = "to", fetch = FetchType.LAZY)
	private List<Transfer> receivedTransfers;

	@ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
	private List<Income> incomes;

	@ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
	private List<Expense> expenses;

	@ManyToMany(mappedBy = "payingUsers", fetch = FetchType.LAZY)
	private List<Expense> paidExpenses;

	protected User(){ }

	public User(String name){
		this.name = name;
		this.balance = 0;

		sentTransfers = new ArrayList<>();
		receivedTransfers = new ArrayList<>();
		incomes = new ArrayList<>();
		expenses = new ArrayList<>();
		paidExpenses = new ArrayList<>();
	}

	public void moneyAdd(double amount){
		this.balance += amount;
	}

	public void moneySub(double amount){
		this.balance -= amount;
	}

	public void setBalance(double balance){
		this.balance = balance;
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

	public List<Transfer> getSentTransfers(){
		return sentTransfers;
	}

	public List<Transfer> getReceivedTransfers(){
		return receivedTransfers;
	}

	public List<Income> getIncomes(){
		return incomes;
	}

	public List<Expense> getExpenses(){
		return expenses;
	}

	public List<Expense> getPaidExpenses(){
		return paidExpenses;
	}

	public List<Transaction> getTransactions(){
		List<Transaction> transactions = new ArrayList<>();
		transactions.addAll(getSentTransfers());
		transactions.addAll(getReceivedTransfers());
		transactions.addAll(getIncomes());
		transactions.addAll(getExpenses());

		return transactions;
	}
}
