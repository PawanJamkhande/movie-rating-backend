package com.movierating.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingResponse {
// flattened, safe view of a Rating - never exposes the full User entity (password!) to the client

	private Long ratingId;

	private Long userId;
	private String userName;

	private Long movieId;
	private String movieTitle;

	private Integer stars;
	private String review;
	private LocalDateTime ratedDate;

}
