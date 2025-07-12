package org.movie.movieapp.repository;

import org.movie.movieapp.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    // Find movies by title (case-insensitive)
    List<Movie> findByTitleContainingIgnoreCase(String title);

    // Find movies by director
    List<Movie> findByDirectorContainingIgnoreCase(String director);

    // Find movies by release year
    List<Movie> findByReleaseYear(Integer releaseYear);

    // Find movies by genre
    @Query("SELECT m FROM Movie m JOIN m.genre g WHERE LOWER(g) LIKE LOWER(CONCAT('%', :genre, '%'))")
    List<Movie> findByGenreContainingIgnoreCase(@Param("genre") String genre);

    // Find movies by IMDb rating range
    List<Movie> findByImdbRatingBetween(Double minRating, Double maxRating);

    // Find movies by release year range
    List<Movie> findByReleaseYearBetween(Integer startYear, Integer endYear);

    // Find top rated movies
    @Query("SELECT m FROM Movie m WHERE m.imdbRating IS NOT NULL ORDER BY m.imdbRating DESC")
    Page<Movie> findTopRatedMovies(Pageable pageable);
}