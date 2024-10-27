package com.rupesh.assignment.movieapplication.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode
public class Movies {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(length = 500)
	private String additionalInfo;

	private String movieyear;

	@Column(length = 1000)
	private String nominee;

	private BigDecimal boxOffice;

	@Column(length = 500)
	private String category;

	private boolean oscarWinner;

	private Integer imdbVotes;

	private Double imdbRating;


}