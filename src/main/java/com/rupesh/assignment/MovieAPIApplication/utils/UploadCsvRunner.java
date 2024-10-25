package com.rupesh.assignment.MovieAPIApplication.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.rupesh.assignment.MovieAPIApplication.movies.MovieEntity;
import com.rupesh.assignment.MovieAPIApplication.movies.MovieRepository;

/**
 * UploadCsvRunner runs on application startup and uploads a CSV file
 * automatically.
 */
@Component
public class UploadCsvRunner implements CommandLineRunner {

	@Value("${csv.file.path}")
	private Resource fileResource;

	private static final Logger log = LoggerFactory.getLogger(UploadCsvRunner.class);

	@Autowired
	private MovieRepository movieRepository;

	@Override
	public void run(String... args) throws Exception {
		uploadCsvFile();
	}

	/**
	 * This method will read all the dataa from CSV file and load the data in our
	 * database as step1. It will also convert Yes or No into Boolean of True or
	 * False for easy of maintainace of code.
	 * 
	 * @param file the CSV file
	 * @throws CsvException
	 */
	public void readCsvAndSaveToDb(MultipartFile file) throws CsvException {
		try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(file.getInputStream())).withSkipLines(1)
				.build()) {
			List<String[]> records = reader.readAll();
			List<MovieEntity> movies = new ArrayList<>();

			for (String[] record : records) {
				try {
					MovieEntity movie = new MovieEntity();
					movie.setMovieyear(record[0]);
					movie.setCategory(record[1]);
					movie.setNominee(record[2]);
					movie.setAdditionalInfo(record[3]);
					movie.setOscarWinner(record[4].equalsIgnoreCase("Yes"));
					movie.setImdbRating((double) 0);
					movie.setImdbVotes(0);
					movie.setBoxOffice(new BigDecimal(0));
					movies.add(movie);
				} catch (Exception e) {
					log.error("Error processing record: " + Arrays.toString(record));
				}
			}

			movieRepository.saveAll(movies);
		} catch (IOException e) {
			log.error("Error reading CSV file", e);
			throw new CsvException("Failed to read CSV file");
		}
	}

	/**
	 * This method will fetch the CSV file from the location and will call
	 * readCsvAndSaveToDb
	 * 
	 * @throws CsvException
	 */
	private void uploadCsvFile() throws CsvException {
		try (FileInputStream input = new FileInputStream(fileResource.getFile())) {
			MultipartFile multipartFile = new MockMultipartFile("file", fileResource.getFilename(), "text/csv", input);
			readCsvAndSaveToDb(multipartFile);
			log.info("CSV data saved successfully");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
