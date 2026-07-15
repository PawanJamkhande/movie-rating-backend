package com.movierating.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.movierating.dto.ApiResponse;
import com.movierating.dto.LoginRequest;
import com.movierating.dto.RegisterRequest;
import com.movierating.dto.UserResponse;
import com.movierating.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserService userService;

	@PostMapping("/register")
	public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest request) {

		UserResponse user = userService.register(request);

		return new ResponseEntity<>(
				new ApiResponse(true, "Registered successfully", user), HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {

		UserResponse user = userService.login(request);

		return ResponseEntity.ok(new ApiResponse(true, "Login successful", user));
	}

}
