package com.filipbudisa.kusur.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class UserIncome implements Serializable {

	@Id
	@ManyToOne
	@JoinColumn
	private User user;

	@Id
	@ManyToOne
	@JoinColumn
	private Income income;

	private double value;

	protected UserIncome(){ }

	public UserIncome(User user, Income income, double value){
		this.user = user;
		this.income = income;
		this.value = value;
	}

	public User getUser(){
		return user;
	}

	public Income getIncome(){
		return income;
	}

	public double getValue(){
		return value;
	}
}
