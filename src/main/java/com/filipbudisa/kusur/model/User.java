package com.filipbudisa.kusur.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	private String name;

	private double balance;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<UserIncome> incomes;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<UserExpense> expenses;

	protected User(){ }

	public User(String name){
		this.name = name;
		this.balance = 0;

		this.incomes = new ArrayList<>();
		this.expenses = new ArrayList<>();
	}

	public void moneyAdd(double amount){
		this.balance -= amount;
	}

	public void moneySub(double amount){
		this.balance += amount;
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

	public List<Transaction> getTransactions(){
		ArrayList<Transaction> transactions = new ArrayList<>();

		List<Transaction> incomeTransactions = incomes.parallelStream()
				.map(UserIncome::getIncome)
				.map(Income::getTransaction)
				.collect(Collectors.toList());

		List<Transaction> expenseTransactions = expenses.parallelStream()
				.map(UserExpense::getExpense)
				.map(Expense::getTransaction)
				.collect(Collectors.toList());

		transactions.addAll(incomeTransactions);
		transactions.addAll(expenseTransactions);

		return transactions;
	}
}
