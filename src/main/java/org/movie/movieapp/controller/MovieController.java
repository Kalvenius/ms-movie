package org.movie.movieapp.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.movie.movieapp.dto.MovieDto;
import org.movie.movieapp.model.Movie;
import org.movie.movieapp.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
@Tag(name = "Movie Management", description = "APIs for managing movie database")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    @Operation(summary = "Get all movies", description = "Retrieve a list of all movies")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved movies")
    public ResponseEntity<List<Movie>> getAllMovies() {
        List<Movie> movies = movieService.getAllMovies();
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/paginated")
    @Operation(summary = "Get movies with pagination", description = "Retrieve movies with pagination and sorting")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated movies")
    public ResponseEntity<Page<Movie>> getAllMoviesPaginated(
            @Parameter(description = "Page number (0-based)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort field") @RequestParam(defaultValue = "title") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "asc") String sortDir) {
        Page<Movie> movies = movieService.getAllMoviesPaginated(page, size, sortBy, sortDir);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get movie by ID", description = "Retrieve a specific movie by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movie found"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    public ResponseEntity<Movie> getMovieById(
            @Parameter(description = "Movie ID") @PathVariable Long id) {
        Movie movie = movieService.getMovieById(id);
        return ResponseEntity.ok(movie);
    }

    @PostMapping
    @Operation(summary = "Create a new movie", description = "Add a new movie to the database")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Movie created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Movie> createMovie(@Valid @RequestBody MovieDto movieDto) {
        Movie createdMovie = movieService.createMovie(movieDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMovie);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a movie", description = "Update an existing movie")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Movie updated successfully"),
            @ApiResponse(responseCode = "404", description = "Movie not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<Movie> updateMovie(
            @Parameter(description = "Movie ID") @PathVariable Long id,
            @Valid @RequestBody MovieDto movieDto) {
        Movie updatedMovie = movieService.updateMovie(id, movieDto);
        return ResponseEntity.ok(updatedMovie);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a movie", description = "Delete a movie from the database")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Movie deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Movie not found")
    })
    public ResponseEntity<Void> deleteMovie(
            @Parameter(description = "Movie ID") @PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search movies", description = "Search movies by various criteria")
    @ApiResponse(responseCode = "200", description = "Search results retrieved")
    public ResponseEntity<List<Movie>> searchMovies(
            @Parameter(description = "Search by title") @RequestParam(required = false) String title,
            @Parameter(description = "Search by director") @RequestParam(required = false) String director,
            @Parameter(description = "Search by genre") @RequestParam(required = false) String genre,
            @Parameter(description = "Search by release year") @RequestParam(required = false) Integer year,
            @Parameter(description = "Minimum IMDb rating") @RequestParam(required = false) Double minRating,
            @Parameter(description = "Maximum IMDb rating") @RequestParam(required = false) Double maxRating) {

        List<Movie> movies;

        if (title != null) {
            movies = movieService.searchMoviesByTitle(title);
        } else if (director != null) {
            movies = movieService.searchMoviesByDirector(director);
        } else if (genre != null) {
            movies = movieService.searchMoviesByGenre(genre);
        } else if (year != null) {
            movies = movieService.searchMoviesByYear(year);
        } else if (minRating != null && maxRating != null) {
            movies = movieService.searchMoviesByRatingRange(minRating, maxRating);
        } else {
            movies = movieService.getAllMovies();
        }

        return ResponseEntity.ok(movies);
    }

    @GetMapping("/top-rated")
    @Operation(summary = "Get top-rated movies", description = "Retrieve top-rated movies")
    @ApiResponse(responseCode = "200", description = "Top-rated movies retrieved")
    public ResponseEntity<Page<Movie>> getTopRatedMovies(
            @Parameter(description = "Page number") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "10") int size) {
        Page<Movie> movies = movieService.getTopRatedMovies(page, size);
        return ResponseEntity.ok(movies);
    }
}