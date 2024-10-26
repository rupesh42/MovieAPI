package com.rupesh.assignment.movieapplication.service;

import java.util.List;

import com.rupesh.assignment.movieapplication.domain.MovieDTO;
import com.rupesh.assignment.movieapplication.domain.Movies;
import com.rupesh.assignment.movieapplication.exception.MovieAPIException;

/**
 * Service class for Main Service Implementation
 * @author Rupesh
 *
 */
public interface MovieService {
    
    /**
     * Updates the ratings for the specified movie.
     * 
     * @param movieName the name of the movie to update ratings for
     * @param newRating the new rating to be added
     * @return a list of updated movie details as MovieDTOs
     * @throws MovieAPIException if the movie is not found or the rating is invalid
     */
    List<MovieDTO> updateRatings(String movieName, double newRating);

    /**
     * Finds movies by their nominee.
     * 
     * @param movieName the name of the nominee to search for
     * @return a list of movie details as MovieDTOs matching the nominee
     * @throws MovieAPIException if no movies are found with the specified nominee
     */
    List<MovieDTO> findMoviesByNominee(String movieName);

    /**
     * Gets the Best Picture winner statement for the specified nominee.
     * 
     * @param nominee the nominee to check
     * @return a statement indicating whether the nominee has won Best Picture
     * @throws ResourceNotFoundException if the movie is not found
     */
    String hastheMovieWonOscars(String nominee);

    /**
     * Retrieves the top 10 movies.
     * 
     * @return a list of top 10 movies
     */
    List<Movies> getTop10();
}

