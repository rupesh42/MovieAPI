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

import com.rupesh.assignment.MovieAPIApplication.movies.MovieEntity;
import com.rupesh.assignment.MovieAPIApplication.movies.MovieRepository;
import com.rupesh.assignment.MovieAPIApplication.utils.CategoryMapper;
import com.rupesh.assignment.MovieAPIApplication.utils.MovieCategory;
import com.rupesh.assignment.MovieAPIApplication.utils.MovieDataUpdater;

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
        MovieEntity movie1 = new MovieEntity();
        movie1.setNominee("Inception");
        movie1.setImdbRating((double) 0);
        movie1.setBoxOffice(BigDecimal.ZERO);
        movie1.setCategory("Best Picture");

        MovieEntity movie2 = new MovieEntity();
        movie2.setNominee("Interstellar");
        movie2.setImdbRating(8.6);
        movie2.setBoxOffice(new BigDecimal("1000000"));
        movie2.setCategory("Best Picture");

        List<MovieEntity> movies = List.of(movie1, movie2);
        when(movieRepository.findAll()).thenReturn(movies);

        try (MockedStatic<CategoryMapper> categoryMapperMock = mockStatic(CategoryMapper.class)) {
            categoryMapperMock.when(() -> CategoryMapper.getCategory("Best Picture")).thenReturn(MovieCategory.MOVIE);

            // Invoke the method
            movieDataUpdater.updateMovieData();

            // Verify that the callExternalAPI method was called for movie1 and not for movie2
            verify(movieDataUpdater).callExternalAPI("Inception", movie1);
            verify(movieDataUpdater, never()).callExternalAPI("Interstellar", movie2);
        }
    }
}
