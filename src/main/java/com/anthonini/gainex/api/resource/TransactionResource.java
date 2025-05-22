package com.anthonini.gainex.api.resource;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.anthonini.gainex.api.event.CreatedResourceEvent;
import com.anthonini.gainex.api.exceptionhandler.GainexExceptionHandler.Error;
import com.anthonini.gainex.api.model.Transaction;
import com.anthonini.gainex.api.repository.filter.TransactionFilter;
import com.anthonini.gainex.api.service.TransactionService;
import com.anthonini.gainex.api.service.exception.NonExistentOrInactivePerson;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransactionResource {
	
	@Autowired
	private TransactionService service;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@Autowired
	private MessageSource messageSource;

	@GetMapping
	public List<Transaction> filter(TransactionFilter filter) {
		return service.filter(filter);
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
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		service.deleteById(id);
	}
	
	@ExceptionHandler({ NonExistentOrInactivePerson.class })
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(NonExistentOrInactivePerson ex) {
		String userMessage = messageSource.getMessage("person.non-existent-or-inactive", null, LocaleContextHolder.getLocale());
		String developerMessage = ex.toString();
		List<Error> errors = Arrays.asList(new Error(userMessage, developerMessage));
		return ResponseEntity.badRequest().body(errors);
	}
}