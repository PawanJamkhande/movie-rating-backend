package com.movierating.service.Impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.movierating.dto.RatingRequest;
import com.movierating.dto.RatingResponse;
import com.movierating.entity.Movie;
import com.movierating.entity.Rating;
import com.movierating.entity.User;
import com.movierating.exception.MovieNotFoundException;
import com.movierating.exception.RatingNotFoundException;
import com.movierating.exception.UserNotFoundException;
import com.movierating.repository.MovieRepository;
import com.movierating.repository.RatingRepository;
import com.movierating.repository.UserRepository;
import com.movierating.service.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MovieRepository movieRepository;

	@Override
	public RatingResponse rateMovie(RatingRequest request) {

		User user = userRepository.findById(request.getUserId())
				.orElseThrow(() -> new UserNotFoundException("User not found with id: " + request.getUserId()));

		Movie movie = movieRepository.findById(request.getMovieId())
				.orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + request.getMovieId()));

		// if this user already rated this movie, update it instead of creating a duplicate row
		Rating rating = ratingRepository
				.findByUser_UserIdAndMovie_MovieId(request.getUserId(), request.getMovieId())
				.orElse(Rating.builder().user(user).movie(movie).build());

		rating.setStars(request.getStars());
		rating.setReview(request.getReview());
		rating.setRatedDate(LocalDateTime.now());

		Rating saved = ratingRepository.save(rating);

		return mapToResponse(saved);
	}

	@Override
	public List<RatingResponse> getRatingsForMovie(Long movieId) {

		if (!movieRepository.existsById(movieId)) {
			throw new MovieNotFoundException("Movie not found with id: " + movieId);
		}

		return ratingRepository.findByMovie_MovieId(movieId)
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	@Override
	public List<RatingResponse> getRatingsByUser(Long userId) {

		if (!userRepository.existsById(userId)) {
			throw new UserNotFoundException("User not found with id: " + userId);
		}

		return ratingRepository.findByUser_UserId(userId)
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	@Override
	public void deleteRating(Long ratingId) {

		Rating rating = ratingRepository.findById(ratingId)
				.orElseThrow(() -> new RatingNotFoundException("Rating not found with id: " + ratingId));

		ratingRepository.delete(rating);
	}

	// flattens the entity graph into a safe DTO - never leaks the User entity (password) to the client
	private RatingResponse mapToResponse(Rating rating) {

		return RatingResponse.builder()
				.ratingId(rating.getRatingId())
				.userId(rating.getUser().getUserId())
				.userName(rating.getUser().getFullName())
				.movieId(rating.getMovie().getMovieId())
				.movieTitle(rating.getMovie().getTitle())
				.stars(rating.getStars())
				.review(rating.getReview())
				.ratedDate(rating.getRatedDate())
				.build();
	}

}
