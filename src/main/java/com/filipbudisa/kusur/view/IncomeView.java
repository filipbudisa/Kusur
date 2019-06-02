package com.filipbudisa.kusur.view;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.filipbudisa.kusur.model.Income;
import com.filipbudisa.kusur.model.MoneyDistribution;
import com.filipbudisa.kusur.model.User;
import com.filipbudisa.kusur.model.UserIncome;

import java.util.List;
import java.util.stream.Collectors;

public class IncomeView {

	private double amount;

	private String distribution;

	private List<UserIncomeView> userIncomes;

	private List<Long> userIds;

	public IncomeView(Income income){
		this.amount = income.getAmount();
		this.distribution = income.getDistribution().toString().toLowerCase();

		if(income.getDistribution() == MoneyDistribution.EQUAL){
			this.userIds = income.getUsers()
					.parallelStream()
					.map(UserIncome::getUser)
					.map(User::getId)
					.collect(Collectors.toList());
		}else{
			this.userIncomes = income.getUsers()
					.parallelStream()
					.map(UserIncomeView::new)
					.collect(Collectors.toList());
		}
	}

	public double getAmount(){
		return amount;
	}

	public String getDistribution(){
		return distribution;
	}

	@JsonProperty("users")
	public Object getUsers(){
		if(userIds == null){
			return userIncomes;
		}else{
			return userIds;
		}
	}
}
