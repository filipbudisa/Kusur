package com.filipbudisa.kusur.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Transaction {

	public enum TransactionType { GENERAL, TRANSFER }

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Enumerated(value = EnumType.ORDINAL)
	private TransactionType type;

	private double amount;

	@OneToOne(mappedBy = "transaction", fetch = FetchType.LAZY)
	private Income income;

	@OneToOne(mappedBy = "transaction", fetch = FetchType.LAZY)
	private Expense expense;

	protected Transaction(){ }

	public Transaction(TransactionType type, double amount){
		this.type = type;
		this.amount = amount;
	}

	public long getId(){
		return id;
	}

	public TransactionType getType(){
		return type;
	}

	public double getAmount(){
		return amount;
	}

	public Income getIncome(){
		return income;
	}

	public Expense getExpense(){
		return expense;
	}

	public void setIncome(Income income){
		this.income = income;
	}

	public void setExpense(Expense expense){
		this.expense = expense;
	}

	@Override
	public boolean equals(Object o){
		if(this == o) return true;
		if(!(o instanceof Transaction)) return false;
		Transaction that = (Transaction) o;
		return id == that.id;
	}

	@Override
	public int hashCode(){
		return Objects.hash(id);
	}
}
