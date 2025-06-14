package com.anthonini.gainex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anthonini.gainex.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
}
