package com.movierating.service;

import com.movierating.dto.LoginRequest;
import com.movierating.dto.RegisterRequest;
import com.movierating.dto.UserResponse;

public interface UserService {

	UserResponse register(RegisterRequest request);

	UserResponse login(LoginRequest request);

}
