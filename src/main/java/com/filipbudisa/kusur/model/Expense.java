package com.filipbudisa.kusur.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Expense {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "transaction_id")
	private Transaction transaction;

	@Enumerated(value = EnumType.ORDINAL)
	private MoneyDistribution distribution;

	private double amount;

	@OneToMany(mappedBy = "expense", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<UserExpense> users;

	protected Expense(){ }

	public Expense(Transaction transaction, MoneyDistribution distribution, double amount){
		this.transaction = transaction;
		this.distribution = distribution;
		this.amount = amount;

		users = new ArrayList<>();
	}

	public void addUserExpense(UserExpense expense){
		users.add(expense);
	}

	public long getId(){
		return id;
	}

	public Transaction getTransaction(){
		return transaction;
	}

	public MoneyDistribution getDistribution(){
		return distribution;
	}

	public double getAmount(){
		return amount;
	}

	public List<UserExpense> getUsers(){
		return users;
	}

	public void setTransaction(Transaction transaction){
		this.transaction = transaction;
	}
}
