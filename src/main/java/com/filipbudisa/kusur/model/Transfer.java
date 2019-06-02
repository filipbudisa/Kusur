package com.filipbudisa.kusur.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Transfer extends Transaction {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "from_user_id")
	private User from;

	@ManyToOne(fetch = FetchType.LAZY)
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
