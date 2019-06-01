package com.filipbudisa.kusur.repository;

import com.filipbudisa.kusur.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
