package com.anthonini.gainex.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anthonini.gainex.api.event.CreatedResourceEvent;
import com.anthonini.gainex.api.model.Transaction;
import com.anthonini.gainex.api.service.TransactionService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransactionResource {
	
	@Autowired
	private TransactionService service;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@GetMapping
	public List<Transaction> list() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Transaction> findById(@PathVariable Long id) {
		Transaction transaction = service.findById(id);
		return transaction != null ? ResponseEntity.ok(transaction) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Transaction> create(@Valid @RequestBody Transaction transaction, HttpServletResponse response) {
		transaction = service.save(transaction);
		publisher.publishEvent(new CreatedResourceEvent(this, response, transaction.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
	}
}