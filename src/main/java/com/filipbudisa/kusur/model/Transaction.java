package com.filipbudisa.kusur.model;

import javax.persistence.*;

@Entity
@Inheritance
public abstract class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	protected double amount;

	public double getAmount(){
		return amount;
	}
}
