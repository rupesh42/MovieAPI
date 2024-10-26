package com.rupesh.assignment.MovieAPIApplication.movies.service;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.exceptions.CsvException;
import com.rupesh.assignment.movieapplication.repository.MovieRepository;
import com.rupesh.assignment.movieapplication.utils.CsvProcessor;

public class CsvServiceTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private CsvProcessor csvService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReadCsvAndSaveToDb() throws CsvException, IOException {
        // Given
        String csvContent = "Year,Category,Nominee,Additional Info,HasWon 2020,Best Picture,Inception,,Yes\n";
        MultipartFile file = new MockMultipartFile("file", "movies.csv", "text/csv", new ByteArrayInputStream(csvContent.getBytes()));

        // When
        csvService.readCsvAndSaveToDb(file);

        // Then
        verify(movieRepository, times(1)).saveAll(anyList());
    }
}
