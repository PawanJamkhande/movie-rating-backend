package com.movierating.service;

import java.util.List;

import com.movierating.dto.RatingRequest;
import com.movierating.dto.RatingResponse;

public interface RatingService {

	// creates a new rating, or updates the user's existing rating for that movie
	RatingResponse rateMovie(RatingRequest request);

	List<RatingResponse> getRatingsForMovie(Long movieId);

	List<RatingResponse> getRatingsByUser(Long userId);

	void deleteRating(Long ratingId);

}
