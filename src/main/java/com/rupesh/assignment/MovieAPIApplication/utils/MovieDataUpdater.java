package com.rupesh.assignment.MovieAPIApplication.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rupesh.assignment.MovieAPIApplication.movies.MovieEntity;
import com.rupesh.assignment.MovieAPIApplication.movies.MovieRepository;

/**
 * This class is important to load the data from API. updateMovieData() will be
 * invoked automatically whenever the application is the ready state.
 * 
 * @author Rupesh
 *
 */
@Component
public class MovieDataUpdater {

	private static final Logger log = LoggerFactory.getLogger(MovieDataUpdater.class);

	private MovieRepository movieRepository;
	
	@Autowired
	public MovieDataUpdater(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	private final RestTemplate restTemplate = new RestTemplate();

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Value("${external.api.key}")
	private String apiKey;

	@Value("${external.api.url}")
	private String apiUrl;
	
	/**
	 * Event Listenr will check the state of thea application and will invoke this
	 * method automatically.
	 */
	@EventListener(ApplicationReadyEvent.class)
	@Profile("!test")
	public void updateMovieData() {
		try {
			List<MovieEntity> movies = movieRepository.findAll();
			for (MovieEntity movie : movies) {
				String movieName = movie.getNominee();
				if (movie.getImdbRating() == 0 || movie.getBoxOffice().intValue() == 0) {
					if (CategoryMapper.getCategory(movie.getCategory()) == MovieCategory.MOVIE) {
						callExternalAPI(movieName, movie);
						log.info("API was called");
					}
				}
			}
		} catch (Exception e) {
			log.error("An unexpected error occurred: {}", e.getMessage(), e);
		}
	}

	
	/**
	 * This method is responsible to call the external API to fetch IMDB rating, votes and BoxOffice value. 
	 * @param movieName
	 * @param movie
	 */
	public void callExternalAPI(String movieName, MovieEntity movie) {
		String url = apiUrl + modifyMovieName(movieName) + "&apikey=" + apiKey;

		try {
			String jsonResponse = restTemplate.getForObject(url, String.class);
			MovieData response = objectMapper.readValue(jsonResponse, MovieData.class);
			if (response != null && response.getBoxOffice() != null && response.getImdbRating() != null) {
				if (CategoryMapper.getCategory(movie.getCategory()) != MovieCategory.OTHER) {
					movie.setBoxOffice(response.getBoxOffice());
					movie.setImdbRating(response.getImdbRating());
					movie.setImdbVotes(Integer.valueOf(response.getImdbVotes().replace(",", "")));
					movieRepository.save(movie);
				}
			}
		} catch (JsonProcessingException e) {
			log.error("Failed to fetch or parse data: " + movie.getNominee());
			e.printStackTrace();
		}

	}
	
	/**
	 * This method will clean the movie name for checking.
	 * for Example: The King's Speech {'Lionel Logue'} will become The King's Speech
	 * Madagascar, carnet de voyage (Madagascar, a Journey Diary) will become Madagascar
	 * 
	 */
	

	public String modifyMovieName(String movie) {
		return movie.split(" \\{")[0].split(" \\(")[0].split(":")[0].split(",")[0];
	}

}
