package com.filipbudisa.kusur.repository;

import com.filipbudisa.kusur.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

}
