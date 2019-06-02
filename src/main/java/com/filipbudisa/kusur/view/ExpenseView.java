package com.filipbudisa.kusur.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.filipbudisa.kusur.model.Expense;
import com.filipbudisa.kusur.model.MoneyDistribution;
import com.filipbudisa.kusur.model.User;
import com.filipbudisa.kusur.model.UserExpense;

import java.util.List;
import java.util.stream.Collectors;

public class ExpenseView {

	private double amount;

	private String distribution;

	private List<UserExpenseView> userExpenses;

	private List<Long> userIds;

	@JsonProperty("transaction_id")
	private Long transactionId;

	public ExpenseView(Expense expense){
		this.amount = expense.getAmount();
		this.distribution = expense.getDistribution().toString().toLowerCase();
		this.transactionId = expense.getTransaction().getId();

		if(expense.getDistribution() == MoneyDistribution.EQUAL){
			this.userIds = expense.getUsers()
					.parallelStream()
					.map(UserExpense::getUser)
					.map(User::getId)
					.collect(Collectors.toList());
		}else{
			this.userExpenses = expense.getUsers()
					.parallelStream()
					.map(UserExpenseView::new)
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
			return userExpenses;
		}else{
			return userIds;
		}
	}

	public Long getTransactionId(){
		return transactionId;
	}
}
