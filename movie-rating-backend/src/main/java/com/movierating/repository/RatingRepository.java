package com.movierating.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.movierating.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {

	List<Rating> findByMovie_MovieId(Long movieId);

	List<Rating> findByUser_UserId(Long userId);

	Optional<Rating> findByUser_UserIdAndMovie_MovieId(Long userId, Long movieId);

	void deleteByMovie_MovieId(Long movieId);

	// average stars for one movie, computed in the DB rather than pulled into Java
	@Query("SELECT AVG(r.stars) FROM Rating r WHERE r.movie.movieId = :movieId")
	Double findAverageRatingByMovieId(@Param("movieId") Long movieId);

	@Query("SELECT COUNT(r) FROM Rating r WHERE r.movie.movieId = :movieId")
	Long countByMovieId(@Param("movieId") Long movieId);

}
