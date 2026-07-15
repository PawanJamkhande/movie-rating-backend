package com.movierating.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.movierating.dto.ApiResponse;
import com.movierating.dto.MovieRequest;
import com.movierating.dto.MovieResponse;
import com.movierating.service.MovieService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

	@Autowired
	private MovieService movieService;

	// ---- Admin operations ----

	@PostMapping("/add")
	public ResponseEntity<ApiResponse> addMovie(@Valid @RequestBody MovieRequest request) {

		MovieResponse movie = movieService.addMovie(request);

		return new ResponseEntity<>(
				new ApiResponse(true, "Movie added successfully", movie), HttpStatus.CREATED);
	}

	@PutMapping("/update/{movieId}")
	public ResponseEntity<ApiResponse> updateMovie(
			@PathVariable Long movieId, @Valid @RequestBody MovieRequest request) {

		MovieResponse movie = movieService.updateMovie(movieId, request);

		return ResponseEntity.ok(new ApiResponse(true, "Movie updated successfully", movie));
	}

	@DeleteMapping("/delete/{movieId}")
	public ResponseEntity<ApiResponse> deleteMovie(@PathVariable Long movieId) {

		movieService.deleteMovie(movieId);

		return ResponseEntity.ok(new ApiResponse(true, "Movie deleted successfully", null));
	}

	// ---- Shared / user operations ----

	@GetMapping("/{movieId}")
	public ResponseEntity<ApiResponse> getMovieById(@PathVariable Long movieId) {

		MovieResponse movie = movieService.getMovieById(movieId);

		return ResponseEntity.ok(new ApiResponse(true, "Movie fetched successfully", movie));
	}

	@GetMapping("/all")
	public ResponseEntity<ApiResponse> getAllMovies() {

		return ResponseEntity.ok(new ApiResponse(true, "Movies fetched successfully", movieService.getAllMovies()));
	}

	@GetMapping("/search")
	public ResponseEntity<ApiResponse> searchByTitle(@RequestParam String title) {

		return ResponseEntity.ok(new ApiResponse(true, "Search results", movieService.searchByTitle(title)));
	}

	@GetMapping("/genre/{genre}")
	public ResponseEntity<ApiResponse> getByGenre(@PathVariable String genre) {

		return ResponseEntity.ok(new ApiResponse(true, "Movies fetched successfully", movieService.getByGenre(genre)));
	}

}
