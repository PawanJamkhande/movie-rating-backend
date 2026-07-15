package com.movierating.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieResponse {
// wraps a Movie together with its computed average rating and total rating count,
// so the frontend never has to calculate this itself

    private Long movieId;
    private String title;
    private List<String> genres;
    private String director;
    private Integer releaseYear;
    private String description;
    private String posterUrl;
    private String language;
    private Integer durationMinutes;
    private String status;

    private Double averageRating;
    private Long totalRatings;

}

