package com.anthonini.gainex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anthonini.gainex.model.Person;

public interface PersonRepository  extends JpaRepository<Person, Long>  {

}
