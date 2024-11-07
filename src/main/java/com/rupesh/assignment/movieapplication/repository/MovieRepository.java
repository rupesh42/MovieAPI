
package com.rupesh.assignment.movieapplication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rupesh.assignment.movieapplication.domain.Movie;


/**
 * This repository has some methods that is required for business logic
 * @author Rupesh
 *
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

	//getTop10
	@Query(value = "SELECT m FROM Movie m ORDER BY m.imdbRating DESC, m.boxOffice DESC")
    List<Movie> findtop10RatedMovies(Pageable pageable);

	//find by name nominee
    List<Movie> findAllByNominee(String nominee);

	//find by name additional info
	List<Movie> findAllByAdditionalInfo(String additionalInfo);

	//update movie rating
	Optional<Movie> findFirstByNominee(String nominee);

	//getBestPicture
	Optional<Movie> findByNomineeAndCategoryAndOscarWinner(String nominee, String category, boolean oscarWinner);

	//find first  by Additional Info
    Optional<Movie> findFirstByAdditionalInfo(String additionalInfo);


}
