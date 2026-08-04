package com.movierating.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.movierating.entity.User;
// JpaRepository consists of 100's of methods that can be inherited using extends keyword
// the method has two arguments User (what entity we are refeerring to ) and Long (the datatype of the primary key of that entity)
public interface UserRepository extends JpaRepository<User, Long> {
	// Spring Data JPA auto-implements these based on method name - no manual query needed

	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	boolean existsByMobile(String mobile);

}
