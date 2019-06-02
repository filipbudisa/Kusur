package com.filipbudisa.kusur.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

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

	@Override
	public boolean equals(Object o){
		if(this == o) return true;
		if(!(o instanceof UserExpense)) return false;
		UserExpense that = (UserExpense) o;
		return Objects.equals(user.getId(), that.user.getId()) &&
				Objects.equals(expense.getId(), that.expense.getId());
	}

	@Override
	public int hashCode(){
		return Objects.hash(user.getId(), expense.getId());
	}
}
