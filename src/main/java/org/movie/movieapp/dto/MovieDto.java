package org.movie.movieapp.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public class MovieDto {
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    private String title;

    @NotBlank(message = "Director is required")
    @Size(min = 1, max = 100, message = "Director name must be between 1 and 100 characters")
    private String director;

    @NotNull(message = "Release year is required")
    @Min(value = 1888, message = "Release year must be after 1888")
    @Max(value = 2030, message = "Release year cannot be in the future")
    private Integer releaseYear;

    private List<String> genre;

    @DecimalMin(value = "0.0", message = "IMDb rating must be at least 0.0")
    @DecimalMax(value = "10.0", message = "IMDb rating cannot exceed 10.0")
    private Double imdbRating;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    @Max(value = 1000, message = "Duration cannot exceed 1000 minutes")
    private Integer duration;

    // Constructors
    public MovieDto() {}

    public MovieDto(String title, String director, Integer releaseYear, List<String> genre,
                    Double imdbRating, String description, Integer duration) {
        this.title = title;
        this.director = director;
        this.releaseYear = releaseYear;
        this.genre = genre;
        this.imdbRating = imdbRating;
        this.description = description;
        this.duration = duration;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public Integer getReleaseYear() { return releaseYear; }
    public void setReleaseYear(Integer releaseYear) { this.releaseYear = releaseYear; }

    public List<String> getGenre() { return genre; }
    public void setGenre(List<String> genre) { this.genre = genre; }

    public Double getImdbRating() { return imdbRating; }
    public void setImdbRating(Double imdbRating) { this.imdbRating = imdbRating; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getDuration() { return duration; }
    public void setDuration(Integer duration) { this.duration = duration; }
}