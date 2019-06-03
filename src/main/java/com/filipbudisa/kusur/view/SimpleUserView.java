package com.filipbudisa.kusur.view;

import com.filipbudisa.kusur.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class SimpleUserView {

	private long id;

	private String name;

	private double balance;

	public SimpleUserView(User user){
		this.id = user.getId();
		this.name = user.getName();
		this.balance = user.getBalance();
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
