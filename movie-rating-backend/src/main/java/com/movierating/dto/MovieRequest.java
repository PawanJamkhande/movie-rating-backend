package com.movierating.dto;

import java.util.List;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MovieRequest {
    // validation annotations make the fields mandatory when admin adds/updates a movie

    @NotBlank(message = "Movie title is required")
    private String title;

    @NotEmpty(message = "At least one genre is required")
    private List<String> genres;

    @NotBlank(message = "Director name is required")
    private String director;

    @Min(value = 1900, message = "Enter a valid release year")
    private Integer releaseYear;

    private String description;

    private String posterUrl;

    @NotBlank(message = "Language is required")
    private String language;

    @Min(value = 1, message = "Duration must be greater than 0")
    private Integer durationMinutes;

}

