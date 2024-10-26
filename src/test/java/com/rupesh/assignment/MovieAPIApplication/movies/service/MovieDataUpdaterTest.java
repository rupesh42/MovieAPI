package com.rupesh.assignment.MovieAPIApplication.movies.service;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.rupesh.assignment.movieapplication.domain.Movies;
import com.rupesh.assignment.movieapplication.repository.MovieRepository;
import com.rupesh.assignment.movieapplication.utils.MovieDataUpdater;
import com.rupesh.assignment.movieapplication.utils.OscarCategory;
import com.rupesh.assignment.movieapplication.utils.OscarCategoryMapper;

@SpringJUnitConfig
public class MovieDataUpdaterTest {

    @Mock
    private MovieRepository movieRepository;

    @Spy
    @InjectMocks
    private MovieDataUpdater movieDataUpdater;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateMovieData() {
        // Given
        Movies movie1 = new Movies();
        movie1.setNominee("Inception");
        movie1.setImdbRating((double) 0);
        movie1.setBoxOffice(BigDecimal.ZERO);
        movie1.setCategory("Best Picture");

        Movies movie2 = new Movies();
        movie2.setNominee("Interstellar");
        movie2.setImdbRating(8.6);
        movie2.setBoxOffice(new BigDecimal("1000000"));
        movie2.setCategory("Best Picture");

        List<Movies> movies = List.of(movie1, movie2);
        when(movieRepository.findAll()).thenReturn(movies);

        try (MockedStatic<OscarCategoryMapper> categoryMapperMock = mockStatic(OscarCategoryMapper.class)) {
            categoryMapperMock.when(() -> OscarCategoryMapper.getCategory("Best Picture")).thenReturn(OscarCategory.MOVIE);

            // Invoke the method
            movieDataUpdater.updateMovieData();

            // Verify that the callExternalAPI method was called for movie1 and not for movie2
            verify(movieDataUpdater).callExternalAPI("Inception", movie1);
            verify(movieDataUpdater, never()).callExternalAPI("Interstellar", movie2);
        }
    }
}
