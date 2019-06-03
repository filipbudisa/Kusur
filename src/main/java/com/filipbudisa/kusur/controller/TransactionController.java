package com.filipbudisa.kusur.controller;

import com.filipbudisa.kusur.exception.DataException;
import com.filipbudisa.kusur.exception.NotFoundException;
import com.filipbudisa.kusur.model.*;
import com.filipbudisa.kusur.repository.ExpenseRepository;
import com.filipbudisa.kusur.repository.IncomeRepository;
import com.filipbudisa.kusur.repository.TransactionRepository;
import com.filipbudisa.kusur.repository.UserRepository;
import com.filipbudisa.kusur.view.TransactionView;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	private TransactionRepository transactionRepo;

	@Autowired
	private IncomeRepository incomeRepo;

	@Autowired
	private ExpenseRepository expenseRepo;

	@Autowired
	private UserRepository userRepo;

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public TransactionView getTransaction(@PathVariable Long id){
		return new TransactionView(transactionRepo.findById(id).get());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TransactionView create(@RequestBody String body) throws Exception {
		JSONObject data = new JSONObject(body);

		Transaction.TransactionType type = Transaction.TransactionType.valueOf(data.getString("type").toUpperCase());

		Transaction transaction;

		if(type == Transaction.TransactionType.TRANSFER){
			transaction = createTransfer(data);
		}else{
			transaction = createGeneric(data);
		}

		return new TransactionView(transaction);
	}

	private Transaction createTransfer(JSONObject data) throws Exception {
		long fromId = data.getLong("from_user_id");
		long toId = data.getLong("to_user_id");

		if(fromId == toId){
			throw new DataException("User can't transfer money to himself");
		}

		User from = userRepo.findById(fromId).orElseThrow(() -> new NotFoundException("user", String.valueOf(fromId)));
		User to = userRepo.findById(toId).orElseThrow(() -> new NotFoundException("user", String.valueOf(toId)));
		Double amount = data.getDouble("amount");

		from.moneySub(amount);
		to.moneyAdd(amount);

		Transaction transaction = transactionRepo.save(new Transaction(Transaction.TransactionType.TRANSFER, amount));

		Income income = new Income(transaction, MoneyDistribution.EQUAL, amount);
		UserIncome userIncome = new UserIncome(from, income, amount);
		income.addUserIncome(userIncome);

		Expense expense = new Expense(transaction, MoneyDistribution.EQUAL, amount);
		UserExpense userExpense = new UserExpense(to, expense, amount);
		expense.addUserExpense(userExpense);

		transaction.setIncome(incomeRepo.save(income));
		transaction.setExpense(expenseRepo.save(expense));

		return transaction;
	}

	private Transaction createGeneric(JSONObject data) throws Exception {
		Double amount = data.getDouble("amount");
		Transaction transaction = new Transaction(Transaction.TransactionType.GENERAL, amount);

		/** ##### Expenses ##### */

		JSONObject $expense = data.getJSONObject("expense");

		MoneyDistribution expenseDistribution;
		try{
			expenseDistribution = MoneyDistribution.valueOf($expense.getString("distribution").toUpperCase());
		}catch(IllegalArgumentException e){
			throw new DataException("Unknown distribution type");
		}

		Expense expense = new Expense(transaction, expenseDistribution, amount);

		if(expenseDistribution == MoneyDistribution.EQUAL){
			JSONArray $users = $expense.getJSONArray("users");
			double value = amount / $users.length();

			for(int i = 0; i < $users.length(); i++){
				expense.addUserExpense(new UserExpense(
						userRepo.findById($users.getLong(i)).get(),
						expense,
						value
				));
			}
		}else{
			JSONArray $users = $expense.getJSONArray("users");
			double sum = 0;

			for(int i = 0; i < $users.length(); i++){
				JSONObject $user = $users.getJSONObject(i);
				double value = $user.getDouble("value");
				sum += value;

				Long userId = $user.getLong("id");

				expense.addUserExpense(new UserExpense(
						userRepo.findById(userId).orElseThrow(() -> new NotFoundException("user", String.valueOf(userId))),
						expense,
						value
				));
			}

			if(expenseDistribution == MoneyDistribution.PERCENTAGE && sum != 100){
				throw new DataException("Percentage sum must be 100");
			}else if(expenseDistribution == MoneyDistribution.ABSOLUTE && sum != amount){
				throw new DataException("Parts sum must be equal to amount");
			}
		}

		/** ##### Incomes ##### */

		JSONObject $income = data.getJSONObject("income");

		MoneyDistribution incomeDistribution;
		try{
			incomeDistribution = MoneyDistribution.valueOf($income.getString("distribution").toUpperCase());
		}catch(IllegalArgumentException e){
			throw new DataException("Unknown distribution type");
		}

		Income income = new Income(transaction, incomeDistribution, amount);

		if(incomeDistribution == MoneyDistribution.EQUAL){
			JSONArray $users = $income.getJSONArray("users");
			double value = amount / $users.length();

			for(int i = 0; i < $users.length(); i++){
				Long userId = $users.getLong(i);

				income.addUserIncome(new UserIncome(
						userRepo.findById(userId).orElseThrow(() -> new NotFoundException("user", String.valueOf(userId))),
						income,
						value
				));
			}
		}else{
			JSONArray $users = $income.getJSONArray("users");
			double sum = 0;

			for(int i = 0; i < $users.length(); i++){
				JSONObject $user = $users.getJSONObject(i);
				double value = $user.getDouble("value");
				sum += value;

				Long userId = $user.getLong("id");

				income.addUserIncome(new UserIncome(
						userRepo.findById($user.getLong("id")).orElseThrow(() -> new NotFoundException("user", String.valueOf(userId))),
						income,
						value
				));
			}

			if(incomeDistribution == MoneyDistribution.PERCENTAGE && sum != 100){
				throw new DataException("Percentage sum must be 100");
			}else if(incomeDistribution == MoneyDistribution.ABSOLUTE && sum != amount){
				throw new DataException("Parts sum must be equal to amount");
			}
		}

		/** ##### Saving ##### */
		transaction = transactionRepo.save(transaction);
		expense.setTransaction(transaction);
		income.setTransaction(transaction);
		transaction.setExpense(expenseRepo.save(expense));
		transaction.setIncome(incomeRepo.save(income));

		/** ##### Updating balances ##### */
		if(expenseDistribution == MoneyDistribution.EQUAL){
			double value = expense.getAmount() / expense.getUsers().size();
			expense.getUsers()
					.stream()
					.map(UserExpense::getUser)
					.forEach(u -> { u.moneyAdd(value); userRepo.save(u); });
		}else{
			for(UserExpense e : expense.getUsers()){
				double value = e.getValue();

				if(expenseDistribution == MoneyDistribution.PERCENTAGE){
					value *= amount / 100;
				}

				User user = e.getUser();
				user.moneyAdd(value);
				userRepo.save(user);
			}
		}

		if(incomeDistribution == MoneyDistribution.EQUAL){
			double value = income.getAmount() / income.getUsers().size();
			income.getUsers()
					.stream()
					.map(UserIncome::getUser)
					.forEach(u -> { u.moneySub(value); userRepo.save(u); });
		}else{
			for(UserIncome e : income.getUsers()){
				double value = e.getValue();

				if(incomeDistribution == MoneyDistribution.PERCENTAGE){
					value *= amount / 100;
				}

				User user = e.getUser();
				user.moneySub(value);
				userRepo.save(user);
			}
		}

		return transaction;
	}
}
