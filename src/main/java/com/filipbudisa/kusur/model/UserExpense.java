package com.filipbudisa.kusur.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
public class UserExpense implements Serializable {

	@Id
	@ManyToOne
	@JoinColumn
	private User user;

	@Id
	@ManyToOne
	@JoinColumn
	private Expense expense;

	private double value;

	protected UserExpense(){ }

	public UserExpense(User user, Expense expense, double value){
		this.user = user;
		this.expense = expense;
		this.value = value;
	}

	public User getUser(){
		return user;
	}

	public Expense getExpense(){
		return expense;
	}

	public double getValue(){
		return value;
	}
}
