package com.filipbudisa.kusur.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.filipbudisa.kusur.model.UserIncome;

public class UserIncomeView {

	@JsonProperty("user_id")
	private long userId;

	private double value;

	public UserIncomeView(UserIncome income){
		this.userId = income.getUser().getId();
		this.value = income.getValue();
	}

	public long getUserId(){
		return userId;
	}

	public double getValue(){
		return value;
	}
}
