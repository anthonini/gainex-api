package com.anthonini.gainex.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.anthonini.gainex.api.model.Person;
import com.anthonini.gainex.api.repository.PersonRepository;

import jakarta.validation.Valid;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	public Person findById(Long id) {
		return personRepository.findById(id).orElse(null);
	}

	public Person save(Person person) {
		return personRepository.save(person);
	}

	public void deleteById(Long id) {
		personRepository.deleteById(id);
	}

	public Person update(Long id, @Valid Person person) {
		Person savedPerson = personRepository.findById(id)
				.orElseThrow(() -> new EmptyResultDataAccessException(1));
		
		BeanUtils.copyProperties(person, savedPerson, "id");
		return personRepository.save(savedPerson);
	}
	
}
