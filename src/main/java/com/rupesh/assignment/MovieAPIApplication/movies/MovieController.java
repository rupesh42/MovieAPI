package com.rupesh.assignment.MovieAPIApplication.movies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController; 

/**
 * Controller for handling movie-related operations.
 * Provides endpoints for updating movie ratings, finding movies by nominee, 
 * retrieving top movies, and to check if the user has won best picture or not.
 */

@RestController 
@RequestMapping("/movies") 
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Updates the ratings for the specified movie.
     * 
     * @param movieName the name of the movie to update ratings for
     * @param newRating the new rating to be added
     * @return a list of updated movie details
     */
    @PostMapping("/updateratings")
    public List<MovieDTO> updateRatingsForMovies(@RequestParam String movieName, @RequestParam double newRating) {
        return movieService.updateRatingsForMovies(movieName, newRating);
    }

    /**
     * Finds movies by their nominee.
     * 
     * @param nominee the nominee to search for
     * @return a list of movies matching the nominee
     */
    @GetMapping("/findByName")
    public ResponseEntity<List<MovieDTO>> findMoviesByNominee(@RequestParam String nominee) {
        return new ResponseEntity<>(movieService.findMoviesByNominee(nominee), HttpStatus.OK);
    }

    /**
     * Retrieves the top 10 movies.
     * 
     * @return a list of top 10 movies
     */
    @GetMapping("/Top10")
    public ResponseEntity<List<MovieEntity>> getTop10Movies() {
        return new ResponseEntity<List<MovieEntity>>(movieService.getTop10(), HttpStatus.OK);
    }

    /**
     * Gets the Best Picture winner statement for the specified nominee.
     * 
     * @param nominee the nominee to check
     * @return a statement indicating whether the nominee has won Best Picture
     */
    @GetMapping("/bestPicture")
    public ResponseEntity<String> getBestPictureWinnerStatement(@RequestParam String nominee) {
        return new ResponseEntity<>(movieService.getBestPictureWinnerStatement(nominee), HttpStatus.OK);
    }
}
