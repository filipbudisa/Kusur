package com.filipbudisa.kusur.model;

import javax.persistence.*;

@Entity
public class MoneyDistributionPart {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne
	@JoinColumn(name = "distribution_id")
	private MoneyDistribution distribution;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	private double value;

	public MoneyDistributionPart(User user, double value){
		this.user = user;
		this.value = value;
	}

	public User getUser(){
		return user;
	}

	public double getValue(){
		return value;
	}
}
