package com.filipbudisa.kusur.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Income extends Transaction {

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_income",
			joinColumns = @JoinColumn(name = "income_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users;

	@OneToOne
	@JoinColumn(name = "distribution_id")
	private MoneyDistribution distribution;

	protected Income(){ }

	public Income(double amount, List<User> users, MoneyDistribution distribution){
		this.amount = amount;
		this.users = users;
		this.distribution = distribution;
	}

	public List<User> getUsers(){
		return users;
	}

	public MoneyDistribution getDistribution(){
		return distribution;
	}
}
