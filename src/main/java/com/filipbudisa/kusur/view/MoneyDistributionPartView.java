package com.filipbudisa.kusur.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.filipbudisa.kusur.model.MoneyDistributionPart;

public class MoneyDistributionPartView {

	@JsonProperty("user_id")
	private long userId;

	private double value;


	public MoneyDistributionPartView(MoneyDistributionPart part){
		this.userId = part.getUser().getId();
		this.value = part.getValue();
	}

	public long getUserId(){
		return userId;
	}

	public double getValue(){
		return value;
	}
}
