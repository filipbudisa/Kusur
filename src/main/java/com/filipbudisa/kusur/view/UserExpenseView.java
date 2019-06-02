package com.filipbudisa.kusur.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.filipbudisa.kusur.model.UserExpense;
import com.filipbudisa.kusur.model.UserIncome;

public class UserExpenseView {

	@JsonProperty("user_id")
	private long userId;

	private double value;

	public UserExpenseView(UserExpense expense){
		this.userId = expense.getUser().getId();
		this.value = expense.getValue();
	}

	public long getUserId(){
		return userId;
	}

	public double getValue(){
		return value;
	}
}
