package com.filipbudisa.kusur.repository;

import com.filipbudisa.kusur.model.Expense;
import org.springframework.data.repository.CrudRepository;

public interface ExpenseRepository extends CrudRepository<Expense, Long> {

}
