package com.filipbudisa.kusur.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	protected double amount;

	public long getId(){
		return id;
	}

	public double getAmount(){
		return amount;
	}
}
