package com.anthonini.gainex.api.resource;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.anthonini.gainex.api.model.Person;
import com.anthonini.gainex.api.repository.PersonRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/persons")
public class PersonResource {

	@Autowired
	private PersonRepository personRepository;
	
	@GetMapping("/{id}")
	public ResponseEntity<Person> buscarPeloCodigo(@PathVariable Long id) {
		Person person = personRepository.findById(id).orElse(null);
		return person != null ? ResponseEntity.ok(person) : ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Person> criar(@Valid @RequestBody Person Person, HttpServletResponse response) {
		Person createdPerson = personRepository.save(Person);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(createdPerson.getId()).toUri();
			response.setHeader("Location", uri.toASCIIString());
			
		return ResponseEntity.created(uri).body(createdPerson);
	}
}
