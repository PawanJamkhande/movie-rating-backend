package com.movierating.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RatingRequest {
    // tells which user is rating which movie, with how many stars + optional review

    @NotNull(message = "User Id is required")
    private Long userId;

    @NotNull(message = "Movie Id is required")
    private Long movieId;

    @NotNull(message = "Stars is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer stars;

    private String review;

}
