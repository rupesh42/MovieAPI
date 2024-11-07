package com.rupesh.assignment.movieapplication.utils;

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
import com.rupesh.assignment.movieapplication.domain.Movie;
import com.rupesh.assignment.movieapplication.repository.MovieRepository;
import jakarta.transaction.Transactional;

/**
 * UploadCsvRunner runs on application startup and uploads a CSV file automatically.
 */
@Component
public class CsvProcessor implements CommandLineRunner {

  @Value("${csv.file.path}")
  private Resource fileResource;

  private static final Logger LOG = LoggerFactory.getLogger(CsvProcessor.class);

  private final MovieRepository movieRepository;

  @Autowired
  public CsvProcessor(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    uploadCsvFile();
  }

  /**
   * This method will read all the dataa from CSV file and load the data in our database as step1.
   * It will also convert Yes or No into Boolean of True or False for easy of maintainace of code.
   * 
   * @param file the CSV file
   * @throws CsvException
   */
  @Transactional
  public void readCsvAndSaveToDb(MultipartFile file) throws CsvException {
    try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(file.getInputStream()))
        .withSkipLines(1).build()) {
      List<String[]> records = reader.readAll();
      List<Movie> movies = new ArrayList<>();

      movieSetter(records, movies);

      movieRepository.saveAll(movies);
    } catch (IOException e) {
      LOG.error(Constant.ERROR_PROCESSING_CSV, e);
    }
  }

  private void movieSetter(List<String[]> records, List<Movie> movies) {
    for (var movieRecord : records) {
      try {
        var movie = new Movie();
        movie.setMovieYear(movieRecord[0]);
        movie.setCategory(movieRecord[1]);
        movie.setNominee(movieRecord[2]);
        movie.setAdditionalInfo(movieRecord[3]);
        movie.setOscarWinner("Yes".equalsIgnoreCase(movieRecord[4]));
        movie.setImdbRating(0.0);
        movie.setImdbVotes(0);
        movie.setBoxOffice(BigDecimal.ZERO);

        movies.add(movie);
      } catch (Exception e) {
        LOG.error(Constant.ERROR_IN_PROCESSING + Arrays.toString(movieRecord), e);
      }
    }
  }

  /**
   * This method will fetch the CSV file from the location and will call readCsvAndSaveToDb
   * 
   * @throws CsvException
   */
  private void uploadCsvFile() throws CsvException {
    try (FileInputStream input = new FileInputStream(fileResource.getFile())) {
      MultipartFile multipartFile =
          new MockMultipartFile("file", fileResource.getFilename(), "text/csv", input);
      readCsvAndSaveToDb(multipartFile);
      LOG.info(Constant.CSV_DATA_SUCCESS);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
