package com.anthonini.gainex.api.repository.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.anthonini.gainex.api.model.Transaction;
import com.anthonini.gainex.api.repository.filter.TransactionFilter;

public interface TransactionRepositoryQuery {

	public Page<Transaction> filter(TransactionFilter filter, Pageable pageable);
}
