package com.anthonini.gainex.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anthonini.gainex.api.model.Person;

public interface PersonRepository  extends JpaRepository<Person, Long>  {

}
