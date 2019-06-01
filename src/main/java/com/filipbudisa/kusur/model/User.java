package com.filipbudisa.kusur.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	private String name;

	private double balance;

	protected User(){ }

	public User(String name){
		this.name = name;
	}

	public void setBalance(double balance){
		this.balance = balance;
	}

	public long getId(){
		return id;
	}

	public String getName(){
		return name;
	}

	public double getBalance(){
		return balance;
	}
}
