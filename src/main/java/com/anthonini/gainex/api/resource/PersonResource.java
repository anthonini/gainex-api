package com.anthonini.gainex.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.anthonini.gainex.api.event.CreatedResourceEvent;
import com.anthonini.gainex.api.model.Person;
import com.anthonini.gainex.api.service.PersonService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/persons")
public class PersonResource {
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping("/{id}")
	public ResponseEntity<Person> findById(@PathVariable Long id) {
		Person person = personService.findById(id);
		return person != null ? ResponseEntity.ok(person) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Person> create(@Valid @RequestBody Person person, HttpServletResponse response) {
		Person createdPerson = personService.save(person);
		publisher.publishEvent(new CreatedResourceEvent(this, response, createdPerson.getId()));
		return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		personService.deleteById(id);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Person> update(@PathVariable Long id, @Valid @RequestBody Person pessoa) {
		Person updatedPerson = personService.update(id, pessoa);
		return ResponseEntity.ok(updatedPerson);
	}
	
	@PutMapping("/{id}/active")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateActiveAttribute(@PathVariable Long id, @RequestBody Boolean active) {
		personService.updateActiveAttribute(id, active);
	}
}
