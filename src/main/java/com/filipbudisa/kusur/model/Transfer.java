package com.filipbudisa.kusur.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Transfer extends Transaction {

	@ManyToOne
	@JoinColumn(name = "from_user_id")
	private User from;

	@ManyToOne
	@JoinColumn(name = "to_user_id")
	private User to;

	protected Transfer(){ }

	public Transfer(User from, User to, double amount){
		this.from = from;
		this.to = to;
		this.amount = amount;
	}

	public User getFrom(){
		return from;
	}

	public User getTo(){
		return to;
	}
}
