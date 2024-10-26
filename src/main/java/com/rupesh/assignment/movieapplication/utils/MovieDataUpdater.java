package com.rupesh.assignment.movieapplication.utils;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rupesh.assignment.movieapplication.domain.MovieData;
import com.rupesh.assignment.movieapplication.domain.Movies;
import com.rupesh.assignment.movieapplication.repository.MovieRepository;

/**
 * This class is important to load the data from API. updateMovieData() will be
 * invoked automatically whenever the application is the ready state.
 * 
 * @author Rupesh
 *
 */
@Component
public class MovieDataUpdater {

	private static final Logger LOG = LoggerFactory.getLogger(MovieDataUpdater.class);

	private final MovieRepository movieRepository;

	private final String apiKey;

	private final String apiUrl;


	private final RestTemplate restTemplate = new RestTemplate();

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	public MovieDataUpdater(MovieRepository movieRepository, @Value("${external.api.key}") String apiKey,
			@Value("${external.api.url}") String apiUrl) {
		this.movieRepository = movieRepository;
		this.apiKey = apiKey;
		this.apiUrl = apiUrl;
	}

	/**
	 * Event Listenr will check the state of thea application and will invoke this
	 * method automatically.
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void updateMovieData() {
		try {
			List<Movies> movies = movieRepository.findAll();
			movies.stream()
					.filter(movie -> (movie.getImdbRating() == 0 || movie.getBoxOffice().intValue() == 0)
							&& OscarCategoryMapper.getCategory(movie.getCategory()) == OscarCategory.MOVIE)
					.forEach(movie -> {
						String movieName = movie.getNominee();
						callExternalAPI(movieName, movie);
					});
			LOG.info("API was called");
		} catch (Exception e) {
			LOG.error("An unexpected error occurred: {}", e.getMessage(), e);
		}
	}

	/**
	 * This method is responsible to call the external API to fetch IMDB rating,
	 * votes and BoxOffice value.
	 * 
	 * @param movieName
	 * @param movie
	 */
	public void callExternalAPI(String movieName, Movies movie) {
		URI uri = UriComponentsBuilder.fromHttpUrl(apiUrl).queryParam("t", modifyMovieName(movieName))
				.queryParam("apikey", apiKey).build().toUri();
		
		String url = uri.toString();
		try {
			String jsonResponse = restTemplate.getForObject(url, String.class);
			MovieData response = objectMapper.readValue(jsonResponse, MovieData.class);
			if (response != null && response.getBoxOffice() != null && response.getImdbRating() != null) {
				movie.setBoxOffice(response.getBoxOffice());
				movie.setImdbRating(response.getImdbRating());
				movie.setImdbVotes(Integer.valueOf(response.getImdbVotes().replace(",", "")));
				movieRepository.save(movie);
			}
		} catch (JsonProcessingException e) {
			LOG.error("Failed to fetch or parse data: ", e);
		}

	}

	/**
	 * This method will clean the movie name for checking. for Example: The King's
	 * Speech {'Lionel Logue'} will become The King's Speech; example: Madagascar,
	 * carnet de voyage (Madagascar, a Journey Diary) will become Madagascar
	 * 
	 */
	public String modifyMovieName(String movie) {
		return movie.replaceAll("\\{.*|\\(.*|:.*|,.*", "").trim();
	}

}
