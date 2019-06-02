package com.filipbudisa.kusur.controller;

import com.filipbudisa.kusur.exception.NotFoundException;
import com.filipbudisa.kusur.model.User;
import com.filipbudisa.kusur.model.UserExpense;
import com.filipbudisa.kusur.model.UserIncome;
import com.filipbudisa.kusur.repository.UserRepository;
import com.filipbudisa.kusur.view.ExpenseView;
import com.filipbudisa.kusur.view.IncomeView;
import com.filipbudisa.kusur.view.TransactionView;
import com.filipbudisa.kusur.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public UserView create(@RequestBody User user){
		return new UserView(userRepo.save(new User(user.getName())));
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public UserView get(@PathVariable long id) throws NotFoundException {
		User user = userRepo.findById(id).orElseThrow(() -> new NotFoundException("user", String.valueOf(id)));
		return new UserView(user);
	}

	@GetMapping("/{id}/transactions")
	@ResponseStatus(HttpStatus.CREATED)
	public List<TransactionView> getTransactions(@PathVariable long id) throws NotFoundException {
		User user = userRepo.findById(id).orElseThrow(() -> new NotFoundException("user", String.valueOf(id)));
		return new UserView(user).getTransactions();
	}

	@GetMapping("/{id}/incomes")
	@ResponseStatus(HttpStatus.CREATED)
	public List<IncomeView> getIncomes(@PathVariable long id) throws NotFoundException {
		User user = userRepo.findById(id).orElseThrow(() -> new NotFoundException("user", String.valueOf(id)));
		return user.getIncomes()
				.parallelStream()
				.map(UserIncome::getIncome)
				.map(IncomeView::new)
				.collect(Collectors.toList());
	}

	@GetMapping("/{id}/expenses")
	@ResponseStatus(HttpStatus.CREATED)
	public List<ExpenseView> getExpenses(@PathVariable long id) throws NotFoundException {
		User user = userRepo.findById(id).orElseThrow(() -> new NotFoundException("user", String.valueOf(id)));
		return user.getExpenses()
				.parallelStream()
				.map(UserExpense::getExpense)
				.map(ExpenseView::new)
				.collect(Collectors.toList());
	}
}
