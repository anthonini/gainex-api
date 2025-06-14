package com.anthonini.gainex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anthonini.gainex.model.Transaction;
import com.anthonini.gainex.repository.transaction.TransactionRepositoryQuery;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, TransactionRepositoryQuery {

}
