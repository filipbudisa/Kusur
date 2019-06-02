package com.filipbudisa.kusur.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

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

	@Override
	public boolean equals(Object o){
		if(this == o) return true;
		if(!(o instanceof UserIncome)) return false;
		UserIncome that = (UserIncome) o;
		return Objects.equals(user.getId(), that.user.getId()) &&
				Objects.equals(income.getId(), that.income.getId());
	}

	@Override
	public int hashCode(){
		return Objects.hash(user.getId(), income.getId());
	}
}
