package com.filipbudisa.kusur.repository;

import com.filipbudisa.kusur.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
	public List<User> findByNameContainingIgnoreCase(String name);
	public List<User> findAll();
}
