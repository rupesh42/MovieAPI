package com.rupesh.assignment.movieapplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieAPI {

	private static final Logger LOG = LoggerFactory.getLogger(MovieAPI.class);

	public static void main(String[] args) {

		SpringApplication.run(MovieAPI.class, args);

		LOG.info("--Application has started--");
	}
}