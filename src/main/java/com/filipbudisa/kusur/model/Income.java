package com.filipbudisa.kusur.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Income extends Transaction {

	@ManyToMany
	@JoinTable(name = "user_income",
			joinColumns = @JoinColumn(name = "income_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users;

	@OneToOne
	@JoinColumn(name = "distribution_id")
	private MoneyDistribution distribution;
}
