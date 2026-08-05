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
	//spring scans this creates an object stores it inside the IOC container
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
				.role("USER")
				.build();

		User saved = userRepository.save(user); // Hibernate inserts the row - table already exists via ddl-auto

		return mapToResponse(saved);
		//mapToResponse is the helper method instead of writing response.setEmailID response.setMobile everytime we write this so it sets the value 
		//mapToResponse is bcz frontend doesnt need to know the password so we dont return the password
	}

	@Override
	public UserResponse login(LoginRequest request) {

		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));
// we used .matches because it compares the given password(that is converted into hashed one) to the hashed password in the database
		//if we simply write request.getpassword.equels(user.getpassword) it will compare the hashed one in the database and the one passwprd taht is not hashed yet so there will be always an exception
		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			// this line sayd if not matches request.getpasword and user.getpassword then throw the error invalis email or password
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
