package com.anthonini.gainex.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anthonini.gainex.api.model.Person;
import com.anthonini.gainex.api.model.Transaction;
import com.anthonini.gainex.api.repository.TransactionRepository;
import com.anthonini.gainex.api.repository.filter.TransactionFilter;
import com.anthonini.gainex.api.service.exception.NonExistentOrInactivePerson;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository repository;
	
	@Autowired
	private PersonService personService;

	public List<Transaction> findAll() {
		return repository.findAll();
	}

	public Transaction findById(Long id) {
		return repository.findById(id).orElse(null);
	}

	public Transaction save(Transaction transaction) {
		Person person = personService.findById(transaction.getPerson().getId());
		if(person == null || person.isInactive()) {
			throw new NonExistentOrInactivePerson();
		}
		
		return repository.save(transaction);
	}

	public Page<Transaction> filter(TransactionFilter filter, Pageable pageable) {
		return repository.filter(filter, pageable);
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}
}
