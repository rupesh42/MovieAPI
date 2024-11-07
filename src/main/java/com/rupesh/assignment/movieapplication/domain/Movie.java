package com.rupesh.assignment.movieapplication.domain;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entity Class for the movie Database
 * @author Rupesh
 *
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(length = 500)
	private String additionalInfo;

	private String movieYear;

	@Column(length = 1000)
	private String nominee;

	private BigDecimal boxOffice;

	@Column(length = 500)
	private String category;

	private boolean oscarWinner;

	private Integer imdbVotes;

	@Max(value = 10)
	private Double imdbRating;


}