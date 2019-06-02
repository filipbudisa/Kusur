package com.filipbudisa.kusur.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Income {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transaction_id")
	private Transaction transaction;

	@Enumerated(value = EnumType.ORDINAL)
	private MoneyDistribution distribution;

	private double amount;

	@OneToMany(mappedBy = "income", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<UserIncome> users;

	protected Income(){ }

	public Income(Transaction transaction, MoneyDistribution distribution, double amount){
		this.transaction = transaction;
		this.distribution = distribution;
		this.amount = amount;

		users = new ArrayList<>();
	}

	public void addUserIncome(UserIncome income){
		users.add(income);
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

	public List<UserIncome> getUsers(){
		return users;
	}
}
