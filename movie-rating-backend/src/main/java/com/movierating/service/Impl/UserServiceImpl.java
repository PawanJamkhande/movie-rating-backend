package com.movierating.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.movierating.dto.LoginRequest;
import com.movierating.dto.RegisterRequest;
import com.movierating.dto.UserResponse;
import com.movierating.entity.User;
import com.movierating.exception.InvalidCredentialsException;
import com.movierating.exception.UserAlreadyExistsException;
import com.movierating.exception.UserNotFoundException;
import com.movierating.repository.UserRepository;
import com.movierating.service.UserService;

@Service // marks this as a Spring managed service bean holding the business logic
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserResponse register(RegisterRequest request) {

		if (userRepository.existsByEmail(request.getEmail())) {
			throw new UserAlreadyExistsException("Email is already registered");
		}

		if (userRepository.existsByMobile(request.getMobile())) {
			throw new UserAlreadyExistsException("Mobile number is already registered");
		}

		User user = User.builder()
				.fullName(request.getFullName())
				.email(request.getEmail())
				.mobile(request.getMobile())
				.password(passwordEncoder.encode(request.getPassword())) // never store plain text password
				.role(request.getRole() == null || request.getRole().isBlank() ? "USER" : request.getRole().toUpperCase())
				.build();

		User saved = userRepository.save(user); // Hibernate inserts the row - table already exists via ddl-auto

		return mapToResponse(saved);
	}

	@Override
	public UserResponse login(LoginRequest request) {

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new InvalidCredentialsException("Invalid email or password");
		}

		return mapToResponse(user);
	}

	private UserResponse mapToResponse(User user) {

		UserResponse response = new UserResponse();

		response.setUserId(user.getUserId());
		response.setFullName(user.getFullName());
		response.setEmail(user.getEmail());
		response.setMobile(user.getMobile());
		response.setRole(user.getRole());

		return response;
	}

}
