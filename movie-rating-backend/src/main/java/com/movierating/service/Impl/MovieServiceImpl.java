package com.movierating.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movierating.dto.MovieRequest;
import com.movierating.dto.MovieResponse;
import com.movierating.entity.Movie;
import com.movierating.exception.MovieAlreadyExistsException;
import com.movierating.exception.MovieNotFoundException;
import com.movierating.repository.MovieRepository;
import com.movierating.repository.RatingRepository;
import com.movierating.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService {

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private RatingRepository ratingRepository;

	@Override
	public MovieResponse addMovie(MovieRequest request) {

		if (movieRepository.existsByTitleIgnoreCaseAndReleaseYear(request.getTitle(), request.getReleaseYear())) {
			throw new MovieAlreadyExistsException(
					"Movie '" + request.getTitle() + "' (" + request.getReleaseYear() + ") already exists");
		}

		Movie movie = Movie.builder()
				.title(request.getTitle())
				.genres(request.getGenres())
				.director(request.getDirector())
				.releaseYear(request.getReleaseYear())
				.description(request.getDescription())
				.posterUrl(request.getPosterUrl())
				.trailerUrl(request.getTrailerUrl())
				.language(request.getLanguage())
				.durationMinutes(request.getDurationMinutes())
				.status("ACTIVE")
				.build();

		Movie saved = movieRepository.save(movie);

		return mapToResponse(saved);
	}

	@Override
	public MovieResponse updateMovie(Long movieId, MovieRequest request) {

		Movie movie = movieRepository.findById(movieId)
				.orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + movieId));

		movie.setTitle(request.getTitle());
		movie.setGenres(request.getGenres());
		movie.setDirector(request.getDirector());
		movie.setReleaseYear(request.getReleaseYear());
		movie.setDescription(request.getDescription());
		movie.setPosterUrl(request.getPosterUrl());
		movie.setTrailerUrl(request.getTrailerUrl());
		movie.setLanguage(request.getLanguage());
		movie.setDurationMinutes(request.getDurationMinutes());

		Movie updated = movieRepository.save(movie);

		return mapToResponse(updated);
	}

	@Override
	public void deleteMovie(Long movieId) {

		Movie movie = movieRepository.findById(movieId)
				.orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + movieId));

		ratingRepository.deleteByMovie_MovieId(movieId); // clean up dependent ratings first

		movieRepository.delete(movie);
	}

	@Override
	public MovieResponse getMovieById(Long movieId) {

		Movie movie = movieRepository.findById(movieId)
				.orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + movieId));

		return mapToResponse(movie);
	}

	@Override
	public List<MovieResponse> getAllMovies() {

		return movieRepository.findAll()
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	@Override
	public List<MovieResponse> searchByTitle(String title) {

		return movieRepository.findByTitleContainingIgnoreCase(title)
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	@Override
	public List<MovieResponse> getByGenre(String genre) {

		// matches movies where ANY of their genres equals the given genre,
		// e.g. a movie tagged [Drama, Horror] is returned for both "Drama" and "Horror"
		return movieRepository.findByGenre(genre)
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	// attaches the live average rating + rating count to every movie sent to the frontend
	private MovieResponse mapToResponse(Movie movie) {

		Double avg = ratingRepository.findAverageRatingByMovieId(movie.getMovieId());
		Long count = ratingRepository.countByMovieId(movie.getMovieId());

		return MovieResponse.builder()
				.movieId(movie.getMovieId())
				.title(movie.getTitle())
				.genres(movie.getGenres())
				.director(movie.getDirector())
				.releaseYear(movie.getReleaseYear())
				.description(movie.getDescription())
				.posterUrl(movie.getPosterUrl())
				.trailerUrl(movie.getTrailerUrl())
				.language(movie.getLanguage())
				.durationMinutes(movie.getDurationMinutes())
				.status(movie.getStatus())
				.averageRating(avg == null ? 0.0 : Math.round(avg * 10.0) / 10.0)
				.totalRatings(count == null ? 0L : count)
				.build();
	}

}
