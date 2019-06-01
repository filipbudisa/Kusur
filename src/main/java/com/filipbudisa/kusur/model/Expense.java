package com.filipbudisa.kusur.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Expense extends Transaction {

	@ManyToMany
	@JoinTable(name = "user_expense",
			joinColumns = @JoinColumn(name = "expense_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users;

	@OneToOne
	@JoinColumn(name = "distribution_id")
	private MoneyDistribution distribution;

	@ManyToMany
	@JoinTable(name = "user_paying_expense",
			joinColumns = @JoinColumn(name = "expense_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> payingUsers;

	@OneToOne
	@JoinColumn(name = "paying_distribution_id")
	private MoneyDistribution payingDistribution;

	protected Expense(){ }
}
