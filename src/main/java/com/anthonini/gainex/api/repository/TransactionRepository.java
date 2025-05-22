package com.anthonini.gainex.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anthonini.gainex.api.model.Transaction;
import com.anthonini.gainex.api.repository.transaction.TransactionRepositoryQuery;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, TransactionRepositoryQuery {

}
