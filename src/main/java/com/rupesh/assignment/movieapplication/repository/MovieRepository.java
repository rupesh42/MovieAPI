
package com.rupesh.assignment.movieapplication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rupesh.assignment.movieapplication.domain.Movies;


/**
 * This repository has some methods that is required for business logic
 * @author Rupesh
 *
 */
@Repository
public interface MovieRepository extends JpaRepository<Movies, Integer> {

	//getTop10
	@Query(value = "SELECT m FROM Movies m ORDER BY m.imdbRating DESC, m.boxOffice DESC")
    List<Movies> findtop10RatedMovies(Pageable pageable);

	//find by name nominee
    List<Movies> findAllByNominee(String nominee);

	//find by name additional info
	List<Movies> findAllByAdditionalInfo(String additionalInfo);

	//update movie rating
	Optional<Movies> findFirstByNominee(String nominee);

	//getBestPicture
	Optional<Movies> findByNomineeAndCategoryAndOscarWinner(String nominee, String category, boolean oscarWinner);

	//find first  by Additional Info
    Optional<Movies> findFirstByAdditionalInfo(String additionalInfo);


}
