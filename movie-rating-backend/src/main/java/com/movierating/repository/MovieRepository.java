package com.movierating.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.movierating.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	boolean existsByTitleIgnoreCaseAndReleaseYear(String title, Integer releaseYear);

	// genres is a @ElementCollection, so this joins into the movie_genres table
	// and matches movies where ANY of their genres equals the given one
	@Query("SELECT DISTINCT m FROM Movie m JOIN m.genres g WHERE LOWER(g) = LOWER(:genre)")
	List<Movie> findByGenre(@Param("genre") String genre);

	List<Movie> findByTitleContainingIgnoreCase(String title);

}

