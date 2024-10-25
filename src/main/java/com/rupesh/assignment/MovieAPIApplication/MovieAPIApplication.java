package com.rupesh.assignment.MovieAPIApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication
public class MovieAPIApplication {

	private static final Logger log = LoggerFactory.getLogger(MovieAPIApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(MovieAPIApplication.class, args);

		log.info("--Application has started--");
	}
}
