
package com.rupesh.assignment.MovieAPIApplication.movies;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * This repository has some methods that is required for business logic
 * @author Rupesh
 *
 */
@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {

	//getTop10
	@Query(value = "SELECT m FROM MovieEntity m ORDER BY m.imdbRating DESC, m.boxOffice DESC")
    List<MovieEntity> findtop10RatedMovies(Pageable pageable);

	//find by name nominee
    List<MovieEntity> findAllByNominee(String nominee);

	//find by name additional info
	List<MovieEntity> findAllByAdditionalInfo(String additionalInfo);

	//update movie rating
	Optional<MovieEntity> findFirstByNominee(String nominee);

	//getBestPicture
	Optional<MovieEntity> findByNomineeAndCategoryAndOscarWinner(String nominee, String category, boolean oscarWinner);

	//find first  by Additional Info
    Optional<MovieEntity> findFirstByAdditionalInfo(String additionalInfo);


}
