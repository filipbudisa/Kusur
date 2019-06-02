package com.filipbudisa.kusur.controller;

import com.filipbudisa.kusur.exception.NotFound;
import com.filipbudisa.kusur.model.Transaction;
import com.filipbudisa.kusur.model.User;
import com.filipbudisa.kusur.repository.UserRepository;
import com.filipbudisa.kusur.view.UserView;
import org.mockito.internal.util.collections.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public User create(@RequestBody User user){
		return userRepo.save(new User(user.getName()));
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public UserView get(@PathVariable long id){
		return new UserView(userRepo.findById(id).get());
	}

	@GetMapping("/{id}/transactions")
	@ResponseStatus(HttpStatus.CREATED)
	public List<Transaction> getTramsactions(@PathVariable long id){
		User user = userRepo.findById(id).get();
		return user.getTransactions();
	}
}
