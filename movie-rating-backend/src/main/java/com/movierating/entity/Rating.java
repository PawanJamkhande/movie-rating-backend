package com.movierating.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ratings",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "movie_id"}))
// unique constraint ensures one user can rate a given movie only once (re-rating updates it)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratingId;

    @ManyToOne // many ratings can belong to one user
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne // many ratings can belong to one movie
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    @Column(nullable = false)
    private Integer stars;

    @Column(length = 1000)
    private String review; // optional written review

    private LocalDateTime ratedDate;

}
