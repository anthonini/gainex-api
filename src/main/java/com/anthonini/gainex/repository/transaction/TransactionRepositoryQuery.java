package com.anthonini.gainex.repository.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anthonini.gainex.model.Transaction;
import com.anthonini.gainex.repository.filter.TransactionFilter;

public interface TransactionRepositoryQuery {

	public Page<Transaction> filter(TransactionFilter filter, Pageable pageable);
}
