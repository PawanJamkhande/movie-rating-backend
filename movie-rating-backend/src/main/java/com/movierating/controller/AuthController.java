package com.movierating.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*; 
// this imports annotations like @RestController, @PostMapping, @GetMapping, @RequestBody, @PathVariable, @RequestParam, @RequestMapping


import com.movierating.dto.ApiResponse;
import com.movierating.dto.LoginRequest;
import com.movierating.dto.RegisterRequest;
import com.movierating.dto.UserResponse;
import com.movierating.service.UserService;

import jakarta.validation.Valid;

@RestController
/*
@RestController = @Controller + @ResponseBody
controller tells the spring that this class receives HTTP requests
response body tells the spring that return the json without this spring will return login.html, 
this converts java object came from UserResponse into json automatically ( using Jackson )
*/
@RequestMapping("/api/auth")
/*
this is common mapping path to make the code more clean 
instead of writing @PostMapping("/api/auth/register") @PostMapping("/api/auth/login") everytime we write @PostMapping("/register") @PostMapping("/login") looks clean
*/
public class AuthController {

	@Autowired
	//inject all the dependency needed here we didnt create the object for UserService it will be fetch from IOC
	private UserService userService;

	@PostMapping("/register")
	//used PostMapping because Post creates the new data, registration always creates new user so PostMapping
	public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest request) {
		//response entity handles the request and returns the status code and all body headers
		// we return to angular the api response wrapped inside the response entity
		// request body created the java object from json 
		// @Valid used to tell the springboot that other annotation that we have used in entity like @NotBlank @Email and all are valid (validation happens)

		UserResponse user = userService.register(request);

		return new ResponseEntity<>(
				new ApiResponse(true, "Registered successfully", user), HttpStatus.CREATED);
		//it returns the response entity with the api respnse in it that says the success are true, and message = user register successfully with user data and the http status
	}
	/*
	When user clicks on regsiter button the 
	Angular -> AuthController -> @RequestBody -> register request DTO -> validation(@valid) -> userService -> User repository -> hibernate -> SQL (data inserted) -> mapToResponse -> API response -> JSON -> angular
	*/

	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request) {

		UserResponse user = userService.login(request);

		return ResponseEntity.ok(new ApiResponse(true, "Login successful", user));
	}

}
