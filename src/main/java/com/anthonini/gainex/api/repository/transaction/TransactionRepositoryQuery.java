package com.anthonini.gainex.api.repository.transaction;

import java.util.List;

import com.anthonini.gainex.api.model.Transaction;
import com.anthonini.gainex.api.repository.filter.TransactionFilter;

public interface TransactionRepositoryQuery {

	public List<Transaction> filter(TransactionFilter filter);
}
