package com.rupesh.assignment.movieapplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rupesh.assignment.movieapplication.domain.MovieDTO;
import com.rupesh.assignment.movieapplication.domain.Movies;
import com.rupesh.assignment.movieapplication.service.MovieServiceImpl; 

/**
 * Controller for handling movie-related operations.
 * Provides endpoints for updating movie ratings, finding movies by nominee, 
 * retrieving top movies, and to check if the user has won best picture or not.
 */

@RestController 
@RequestMapping("/movies") 
public class MovieController {

    private final MovieServiceImpl movieService;

    @Autowired
    public MovieController(MovieServiceImpl movieService) {
        this.movieService = movieService;
    }

    /**
     * Updates the ratings for the specified movie.
     * 
     * @param movieName the name of the movie to update ratings for
     * @param newRating the new rating to be added
     * @return a list of updated movie details
     */
    @PostMapping("/updateRatings")
    public List<MovieDTO> updateRatings(@RequestParam String movieName, @RequestParam double newRating) {
        return movieService.updateRatings(movieName, newRating);
    }

    /**
     * Finds movies by their nominee.
     * 
     * @param movieName the nominee to search for
     * @return a list of movies matching the nominee
     */
    @GetMapping("/findByName")
    public ResponseEntity<List<MovieDTO>> findMoviesByNominee(@RequestParam String movieName) {
        return new ResponseEntity<>(movieService.findMoviesByNominee(movieName), HttpStatus.OK);
    }

    /**
     * Retrieves the top 10 movies.
     * 
     * @return a list of top 10 movies
     */
    @GetMapping("/top10")
    public ResponseEntity<List<Movies>> getTop10Movies() {
        return new ResponseEntity<>(movieService.getTop10(), HttpStatus.OK);
    }

    /**
     * Gets the Best Picture winner statement for the specified nominee.
     * 
     * @param movieName the nominee to check
     * @return a statement indicating whether the nominee has won Best Picture
     */
    @GetMapping("/isOscarBestPicture")
    public ResponseEntity<String> hastheMovieWonOscars(@RequestParam String movieName) {
        return new ResponseEntity<>(movieService.hastheMovieWonOscars(movieName), HttpStatus.OK);
    }
}
