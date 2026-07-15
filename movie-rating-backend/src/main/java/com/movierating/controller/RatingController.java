package com.movierating.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.movierating.dto.ApiResponse;
import com.movierating.dto.RatingRequest;
import com.movierating.dto.RatingResponse;
import com.movierating.service.RatingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

	@Autowired
	private RatingService ratingService;

	// user submits or updates their rating for a movie
	@PostMapping("/rate")
	public ResponseEntity<ApiResponse> rateMovie(@Valid @RequestBody RatingRequest request) {

		RatingResponse rating = ratingService.rateMovie(request);

		return new ResponseEntity<>(
				new ApiResponse(true, "Rating submitted successfully", rating), HttpStatus.CREATED);
	}

	@GetMapping("/movie/{movieId}")
	public ResponseEntity<ApiResponse> getRatingsForMovie(@PathVariable Long movieId) {

		return ResponseEntity.ok(
				new ApiResponse(true, "Ratings fetched successfully", ratingService.getRatingsForMovie(movieId)));
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<ApiResponse> getRatingsByUser(@PathVariable Long userId) {

		return ResponseEntity.ok(
				new ApiResponse(true, "Ratings fetched successfully", ratingService.getRatingsByUser(userId)));
	}

	@DeleteMapping("/delete/{ratingId}")
	public ResponseEntity<ApiResponse> deleteRating(@PathVariable Long ratingId) {

		ratingService.deleteRating(ratingId);

		return ResponseEntity.ok(new ApiResponse(true, "Rating deleted successfully", null));
	}

}
