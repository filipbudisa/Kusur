package com.filipbudisa.kusur.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.filipbudisa.kusur.model.Income;
import com.filipbudisa.kusur.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class IncomeView extends TransactionView {

	@JsonProperty("users")
	private List<Long> userIds;

	private MoneyDistributionView distribution;

	public IncomeView(Income income){
		super(income);

		this.userIds = income.getUsers()
				.parallelStream()
				.map(User::getId)
				.collect(Collectors.toList());

		this.distribution = new MoneyDistributionView(income.getDistribution());
	}
}
