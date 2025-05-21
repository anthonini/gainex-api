package com.anthonini.gainex.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.anthonini.gainex.api.event.CreatedResourceEvent;
import com.anthonini.gainex.api.model.Person;
import com.anthonini.gainex.api.repository.PersonRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/persons")
public class PersonResource {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping("/{id}")
	public ResponseEntity<Person> buscarPeloCodigo(@PathVariable Long id) {
		Person person = personRepository.findById(id).orElse(null);
		return person != null ? ResponseEntity.ok(person) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Person> criar(@Valid @RequestBody Person Person, HttpServletResponse response) {
		Person createdPerson = personRepository.save(Person);
		publisher.publishEvent(new CreatedResourceEvent(this, response, createdPerson.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long id) {
		personRepository.deleteById(id);
	}
}
