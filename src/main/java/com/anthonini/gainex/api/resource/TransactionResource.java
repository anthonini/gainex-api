package com.anthonini.gainex.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anthonini.gainex.api.model.Transaction;
import com.anthonini.gainex.api.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionResource {
	
	@Autowired
	private TransactionService service;

	@GetMapping
	public List<Transaction> list() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Transaction> findById(@PathVariable Long id) {
		Transaction transaction = service.findById(id);
		return transaction != null ? ResponseEntity.ok(transaction) : ResponseEntity.notFound().build();
	}
}
