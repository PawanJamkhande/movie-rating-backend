package com.movierating.service;

import java.util.List;

import com.movierating.dto.MovieRequest;
import com.movierating.dto.MovieResponse;

public interface MovieService {

	MovieResponse addMovie(MovieRequest request);

	MovieResponse updateMovie(Long movieId, MovieRequest request);

	void deleteMovie(Long movieId);

	MovieResponse getMovieById(Long movieId);

	List<MovieResponse> getAllMovies();

	List<MovieResponse> searchByTitle(String title);

	List<MovieResponse> getByGenre(String genre);

}
