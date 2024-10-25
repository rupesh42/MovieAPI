package com.rupesh.assignment.MovieAPIApplication.movies.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.rupesh.assignment.MovieAPIApplication.exception.CustomException;
import com.rupesh.assignment.MovieAPIApplication.movies.MovieDTO;
import com.rupesh.assignment.MovieAPIApplication.movies.MovieEntity;
import com.rupesh.assignment.MovieAPIApplication.movies.MovieRepository;
import com.rupesh.assignment.MovieAPIApplication.movies.MovieService;
import com.rupesh.assignment.MovieAPIApplication.utils.MovieDataUpdater;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

	@Mock
	private MovieRepository movieRepository;

	@Spy
	@InjectMocks
	private MovieService movieService;

	@Mock
	MovieDataUpdater movieDataUpdater;

	@Test
	public void testUpdateRatingsForMovies() throws JsonMappingException, JsonProcessingException {
		String movieName = "Inception";
		double newRating = 9.0;

		MovieEntity movie1 = new MovieEntity();
		movie1.setNominee(movieName);
		movie1.setImdbRating(8.5);
		movie1.setImdbVotes(1000);

		List<MovieEntity> movies = Arrays.asList(movie1);

		when(movieRepository.findAllByNominee(movieName)).thenReturn(movies);
		when(movieRepository.saveAll(anyList())).thenReturn(movies);

		// Mock the external API call
		doNothing().when(movieDataUpdater).callExternalAPI(anyString(), any(MovieEntity.class));

		List<MovieDTO> updatedMovies = movieService.updateRatingsForMovies(movieName, newRating);

		assertNotNull(updatedMovies);
		assertEquals(1, updatedMovies.size());
		assertEquals(movieName, updatedMovies.get(0).getNominee());
		assertEquals(1001, updatedMovies.get(0).getImdbVotes());
		assertEquals(8.51, updatedMovies.get(0).getImdbRating()); // Adjust this based on your rounding logic
	}

	@Test
	public void testFindMoviesByNominee_HappyPath() {
		String movieName = "Inception";
		MovieEntity movieEntity = new MovieEntity();
		movieEntity.setNominee(movieName);
		movieEntity.setCategory("Best Picture");
		List<MovieEntity> movies = List.of(movieEntity);

		// Ensure repository methods are called on the mock object
		when(movieRepository.findFirstByNominee(movieName)).thenReturn(Optional.of(movieEntity));
		when(movieDataUpdater.modifyMovieName(movieName)).thenReturn(movieName);
		when(movieRepository.findAllByNominee(movieName)).thenReturn(movies);

		List<MovieDTO> result = movieService.findMoviesByNominee(movieName);

		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(movieName, result.get(0).getNominee());
	}

	@Test
	public void testGetMoviesByNominee_FoundInNominee() {
		String movieName = "Inception";
		MovieEntity movieEntity = new MovieEntity();
		movieEntity.setNominee(movieName);
		movieEntity.setCategory("Best Picture");
		when(movieRepository.findFirstByNominee(movieName)).thenReturn(Optional.of(movieEntity));
		when(movieDataUpdater.modifyMovieName(movieName)).thenReturn(movieName);
		when(movieRepository.findAllByNominee(movieName)).thenReturn(List.of(movieEntity));

		List<MovieDTO> result = movieService.findMoviesByNominee(movieName);

		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(movieName, result.get(0).getNominee());
	}

	@Test
	public void testGetMoviesByNominee_FoundInAdditionalInfo() {
		String movieName = "Inception";
		MovieEntity movieEntity = new MovieEntity();
		movieEntity.setCategory("Actor -- Leading Role");
		movieEntity.setNominee(movieName);
		when(movieRepository.findFirstByNominee(movieName)).thenReturn(Optional.empty());
		when(movieRepository.findFirstByAdditionalInfo(movieName)).thenReturn(Optional.of(movieEntity));
		when(movieDataUpdater.modifyMovieName(movieName)).thenReturn(movieName);
		when(movieRepository.findAllByAdditionalInfo(movieName)).thenReturn(List.of(movieEntity));

		List<MovieDTO> result = movieService.findMoviesByNominee(movieName);

		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals(movieName, result.get(0).getNominee());
	}

	@Test
	public void testGetMoviesByNominee_NotFound() {
		String movieName = "NonExistentMovie";
		when(movieRepository.findFirstByNominee(movieName)).thenReturn(Optional.empty());
		when(movieRepository.findFirstByAdditionalInfo(movieName)).thenReturn(Optional.empty());

		// Expect the CustomException to be thrown
		CustomException thrown = assertThrows(CustomException.class, () -> {
			movieService.findMoviesByNominee(movieName);
		});

		assertEquals("Movie not found with name: " + movieName, thrown.getMessage());
	}

	@Test
	public void testGetBestPictureWinnerStatement_Winner() {
		String nominee = "Inception";
		MovieEntity movie = new MovieEntity();
		movie.setNominee("Inception");
		movie.setCategory("Best Picture");
		movie.setOscarWinner(true);

		when(movieRepository.findByNomineeAndCategoryAndOscarWinner(nominee, "Best Picture", true))
				.thenReturn(Optional.of(movie));

		String result = movieService.getBestPictureWinnerStatement(nominee);

		assertEquals(nominee + " has won the Best Picture!", result);
	}

	@Test
	public void testGetBestPictureWinnerStatement_HasNotWon() {
		// Given
		String nominee = "Inception";
		when(movieRepository.findByNomineeAndCategoryAndOscarWinner(nominee, "Best Picture", true))
				.thenReturn(Optional.empty());
		when(movieRepository.findFirstByNominee(nominee)).thenReturn(Optional.of(new MovieEntity()));

		// When
		String result = movieService.getBestPictureWinnerStatement(nominee);

		// Then
		assertEquals(nominee + " has not won the Best Picture.", result);
	}

	@Test
	public void testGetBestPictureWinnerStatement_MovieNotFound() {
		// Given
		String nominee = "NonExistentMovie";
		when(movieRepository.findByNomineeAndCategoryAndOscarWinner(nominee, "Best Picture", true))
				.thenReturn(Optional.empty());
		when(movieRepository.findFirstByNominee(nominee)).thenReturn(Optional.empty());

		// When & Then
		CustomException thrown = assertThrows(CustomException.class, () -> {
			movieService.getBestPictureWinnerStatement(nominee);
		});

		assertEquals("Movie not found with name: " + nominee, thrown.getMessage());
	}

	@Test
	public void testGetTop10() {
		Pageable pageable = PageRequest.of(0, 10);

		MovieEntity movie1 = new MovieEntity();
		movie1.setNominee("Inception");
		movie1.setImdbRating(8.8);
		movie1.setBoxOffice(new BigDecimal("829.89"));

		MovieEntity movie2 = new MovieEntity();
		movie2.setNominee("The Dark Knight");
		movie2.setImdbRating(9.0);
		movie2.setBoxOffice(new BigDecimal("1004.56"));

		List<MovieEntity> movies = Arrays.asList(movie1, movie2);

		when(movieRepository.findtop10RatedMovies(pageable)).thenReturn(movies);

		List<MovieEntity> result = movieService.getTop10();

		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals("Inception", result.get(0).getNominee());
		assertEquals(new BigDecimal("829.89"), result.get(0).getBoxOffice());
		assertEquals("The Dark Knight", result.get(1).getNominee());
		assertEquals(new BigDecimal("1004.56"), result.get(1).getBoxOffice());
	}

}