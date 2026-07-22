package com.movierating.entity;

import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Entity // marks this class as a database table
@Table(name = "movies")
@Data // generates getters/setters/toString/equals/hashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder // builder pattern for object creation
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment primary key
    private Long movieId;

    @NotBlank(message = "Movie title is required")
    @Column(nullable = false)
    private String title;

    // a movie can belong to more than one genre (e.g. Drama + Horror), so this is a
    // list, not a single string. @ElementCollection makes Hibernate auto-create a
    // separate "movie_genres" table (movie_id, genre) - no manual SQL needed, same
    // as every other table in this project.
    @NotEmpty(message = "At least one genre is required")
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "movie_genres", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "genre", nullable = false) //@Column automatically creates the coloumn done by JPA/Hibernate
    private List<String> genres;

    @NotBlank(message = "Director name is required")
    @Column(nullable = false)
    private String director;

    @Min(value = 1900, message = "Enter a valid release year")
    @Column(nullable = false)
    private Integer releaseYear;

    @Column(length = 1000)
    private String description;

    // URL to poster image, optional
    private String posterUrl;
    // YouTube trailer URL, optional
    private String trailerUrl;

    @Column(nullable = false)
    private String language;

    @Min(value = 1, message = "Duration must be greater than 0")
    private Integer durationMinutes;

    // ACTIVE by default, could be used for soft-hide instead of hard delete
    @Column(nullable = false)
    private String status;

}

