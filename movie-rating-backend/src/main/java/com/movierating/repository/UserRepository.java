package com.movierating.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movierating.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	// Spring Data JPA auto-implements these based on method name - no manual query needed

	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByMobile(String mobile);

}
