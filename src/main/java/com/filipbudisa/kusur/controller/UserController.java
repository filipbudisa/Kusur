package com.filipbudisa.kusur.controller;

import com.filipbudisa.kusur.model.User;
import com.filipbudisa.kusur.repository.UserRepository;
import com.filipbudisa.kusur.view.TransactionView;
import com.filipbudisa.kusur.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
	public UserView get(@PathVariable long id){
		return new UserView(userRepo.findById(id).get());
	}

	@GetMapping("/{id}/transactions")
	@ResponseStatus(HttpStatus.CREATED)
	public List<TransactionView> getTransactions(@PathVariable long id){
		User user = userRepo.findById(id).get();
		return new UserView(user).getTransactions();
	}
}
