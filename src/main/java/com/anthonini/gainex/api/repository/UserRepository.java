package com.anthonini.gainex.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anthonini.gainex.api.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByEmail(String email);
}
