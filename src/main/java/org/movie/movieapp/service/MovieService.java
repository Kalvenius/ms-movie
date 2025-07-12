package org.movie.movieapp.service;

import org.movie.movieapp.dto.MovieDto;
import org.movie.movieapp.exception.MovieNotFoundException;
import org.movie.movieapp.model.Movie;
import org.movie.movieapp.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Page<Movie> getAllMoviesPaginated(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return movieRepository.findAll(pageable);
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(id));
    }

    public Movie createMovie(MovieDto movieDto) {
        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setDirector(movieDto.getDirector());
        movie.setReleaseYear(movieDto.getReleaseYear());
        movie.setGenre(movieDto.getGenre());
        movie.setImdbRating(movieDto.getImdbRating());
        movie.setDescription(movieDto.getDescription());
        movie.setDuration(movieDto.getDuration());

        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long id, MovieDto movieDto) {
        Movie movie = getMovieById(id);

        movie.setTitle(movieDto.getTitle());
        movie.setDirector(movieDto.getDirector());
        movie.setReleaseYear(movieDto.getReleaseYear());
        movie.setGenre(movieDto.getGenre());
        movie.setImdbRating(movieDto.getImdbRating());
        movie.setDescription(movieDto.getDescription());
        movie.setDuration(movieDto.getDuration());

        return movieRepository.save(movie);
    }

    public void deleteMovie(Long id) {
        Movie movie = getMovieById(id);
        movieRepository.delete(movie);
    }

    public List<Movie> searchMoviesByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Movie> searchMoviesByDirector(String director) {
        return movieRepository.findByDirectorContainingIgnoreCase(director);
    }

    public List<Movie> searchMoviesByGenre(String genre) {
        return movieRepository.findByGenreContainingIgnoreCase(genre);
    }

    public List<Movie> searchMoviesByYear(Integer year) {
        return movieRepository.findByReleaseYear(year);
    }

    public List<Movie> searchMoviesByRatingRange(Double minRating, Double maxRating) {
        return movieRepository.findByImdbRatingBetween(minRating, maxRating);
    }

    public Page<Movie> getTopRatedMovies(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return movieRepository.findTopRatedMovies(pageable);
    }
}