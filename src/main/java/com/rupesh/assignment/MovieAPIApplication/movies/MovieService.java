package com.rupesh.assignment.MovieAPIApplication.movies;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rupesh.assignment.MovieAPIApplication.exception.CustomException;
import com.rupesh.assignment.MovieAPIApplication.utils.CategoryMapper;
import com.rupesh.assignment.MovieAPIApplication.utils.MovieCategory;
import com.rupesh.assignment.MovieAPIApplication.utils.MovieDataUpdater;

/**
 * Service for handling movie-related operations. Business logics for updating
 * movie ratings, finding movies by nominee, retrieving top movies, and to check
 * if the user has won best picture or not.
 */

@Service
public class MovieService {

	private final MovieRepository movieRepository;

	private final MovieDataUpdater movieDataUpdater;

	@Autowired
	public MovieService(MovieRepository movieRepository, MovieDataUpdater movieDataUpdater) {
		this.movieRepository = movieRepository;
		this.movieDataUpdater = movieDataUpdater;
	}
	
	private final static String MOVIE_NOT_FOUND = "Movie not found with name: ";
	/**
	 * Updates the ratings for the specified movie and the rating will be provided
	 * by user.
	 * 
	 * @param movieName the name of the movie to update ratings for
	 * @param newRating the new rating to be added
	 * @return a list of updated movie details as MovieDTOs
	 * @throws CustomException if the movie is not found or the rating is less than
	 *                         1
	 */

	public List<MovieDTO> updateRatingsForMovies(String movieName, double newRating) {
		List<MovieEntity> movies = movieRepository.findAllByNominee(movieName);
		movies.stream().findFirst().orElseThrow(() -> new CustomException(MOVIE_NOT_FOUND + movieName));
		if (newRating < 1)
			throw new CustomException("Minimum rating is 1 " + newRating);
		for (MovieEntity movie : movies) {
			movieDataUpdater.callExternalAPI(movieName, movie);

			int imdbVotes = movie.getImdbVotes();
			double totalRatingSum = (movie.getImdbRating() * imdbVotes) + newRating;
			double newAverageRating = totalRatingSum / (imdbVotes + 1);

			double roundedAverageRating = BigDecimal.valueOf(newAverageRating).setScale(2, RoundingMode.UP)
					.doubleValue();
			movie.setImdbVotes(imdbVotes + 1);
			movie.setImdbRating(roundedAverageRating);
		}

		List<MovieEntity> updatedMovies = movieRepository.saveAll(movies);
		return updatedMovies.stream().map(this::convertToDTO).collect(Collectors.toList());

	}

	/**
	 * Finds movies by their name.
	 * 
	 * @param movieName the name of the nominee to search for
	 * @return a list of movie details as MovieDTOs matching the nominee
	 * @throws CustomException if no movies are found with the specified nominee
	 */

	public List<MovieDTO> findMoviesByNominee(String movieName) {
		List<MovieEntity> movies = getMoviesByNominee(movieName);

		Optional.of(movies).filter(m -> !m.isEmpty())
				.orElseThrow(() -> new CustomException(MOVIE_NOT_FOUND+ movieName));

		return movies.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	/**
	 * Retrieves movies by their nominee, checking both nominee and additional info
	 * fields.
	 * 
	 * @param movieName the name of the nominee to search for
	 * @return a list of MovieEntity objects matching the nominee As the CSV file
	 *         data, of the movie category is Actor related, the movie name will be
	 *         found in Additional_Info column without the charachter name of actor.
	 *         This logic is handled here in this method.
	 */

	private List<MovieEntity> getMoviesByNominee(String movieName) {
		// First, attempt to find the category based on the movie name
		Optional<MovieEntity> movieOptional = movieRepository.findFirstByNominee(movieName);
		if (movieOptional.isPresent()) {
			MovieEntity movie = movieOptional.get();
			MovieCategory category = CategoryMapper.getCategory(movie.getCategory());
			if (category == MovieCategory.ACTORS) {
				return movieRepository.findAllByAdditionalInfo(movieDataUpdater.modifyMovieName(movieName));
			} else {
				return movieRepository.findAllByNominee(movieDataUpdater.modifyMovieName(movieName));
			}
		}
		// If not found in nominee, try finding in additional info
		movieOptional = movieRepository.findFirstByAdditionalInfo(movieName);
		if (movieOptional.isPresent()) {
			MovieEntity movie = movieOptional.get();
			MovieCategory category = CategoryMapper.getCategory(movie.getCategory());
			if (category == MovieCategory.ACTORS) {
				return movieRepository.findAllByAdditionalInfo(movieDataUpdater.modifyMovieName(movieName));
			} else {
				return movieRepository.findAllByNominee(movieDataUpdater.modifyMovieName(movieName));
			}
		}
		// If no category match, return an empty list
		return Collections.emptyList();
	}

	/**
	 * Gets the Best Picture winner statement for the specified nominee.
	 * 
	 * @param nominee the nominee to check
	 * @return a statement indicating whether the nominee has won Best Picture
	 * @throws ResourceNotFoundException if the movie is not found
	 */

	public String getBestPictureWinnerStatement(String nominee) {
		Optional<MovieEntity> movie = movieRepository.findByNomineeAndCategoryAndOscarWinner(nominee, "Best Picture",
				true);

		if (movie.isPresent()) 
			return nominee + " has won the Best Picture!";
		 else {
			movieRepository.findFirstByNominee(nominee).orElseThrow(() -> new CustomException(MOVIE_NOT_FOUND+ nominee));
			return nominee + " has not won the Best Picture.";
		}
	}

	/**
	 * Retrieves top 10 rated movies ordered by Box Office collection
	 * 
	 * @return a list of MovieEntity of movies.
	 */

	public List<MovieEntity> getTop10() {
		Pageable pageable = PageRequest.of(0, 10);
		return movieRepository.findtop10RatedMovies(pageable);
	}

	/**
	 * This is the mapper from Entity to DTO.
	 * 
	 * @param movieEntity class MovieEntity
	 * @return MovieDTO object
	 */
	private MovieDTO convertToDTO(MovieEntity movieEntity) {
		MovieDTO movieDTO = new MovieDTO();
		movieDTO.setId(movieEntity.getId());
		movieDTO.setAdditionalInfo(movieEntity.getAdditionalInfo());
		movieDTO.setMovieyear(movieEntity.getMovieyear());
		movieDTO.setNominee(movieEntity.getNominee());
		movieDTO.setBoxOffice(movieEntity.getBoxOffice());
		movieDTO.setCategory(movieEntity.getCategory());
		movieDTO.setOscarWinner(movieEntity.isOscarWinner());
		movieDTO.setImdbVotes(movieEntity.getImdbVotes());
		movieDTO.setImdbRating(movieEntity.getImdbRating());
		return movieDTO;
	}

}
