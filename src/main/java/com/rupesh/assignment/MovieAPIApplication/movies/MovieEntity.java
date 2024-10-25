package com.rupesh.assignment.MovieAPIApplication.movies;

import java.math.BigDecimal;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MovieEntity {

	public MovieEntity() {
	}

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


	@Override
	public int hashCode() {
		return Objects.hash(additionalInfo, boxOffice, category, id, imdbRating, imdbVotes, movieyear, nominee,
				oscarWinner);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof MovieEntity)) {
			return false;
		}
		MovieEntity other = (MovieEntity) obj;
		return Objects.equals(additionalInfo, other.additionalInfo) && Objects.equals(boxOffice, other.boxOffice)
				&& Objects.equals(category, other.category) && Objects.equals(id, other.id)
				&& Objects.equals(imdbRating, other.imdbRating) && Objects.equals(imdbVotes, other.imdbVotes)
				&& Objects.equals(movieyear, other.movieyear) && Objects.equals(nominee, other.nominee)
				&& oscarWinner == other.oscarWinner;
	}

	public MovieEntity(Integer id, String additionalInfo, String movieyear, String nominee, BigDecimal boxOffice,
			String category, boolean oscarWinner, Integer imdbVotes, Double imdbRating) {
		super();
		this.id = id;
		this.additionalInfo = additionalInfo;
		this.movieyear = movieyear;
		this.nominee = nominee;
		this.boxOffice = boxOffice;
		this.category = category;
		this.oscarWinner = oscarWinner;
		this.imdbVotes = imdbVotes;
		this.imdbRating = imdbRating;
	}

	@Override
	public String toString() {
		return "MovieEntity [id=" + id + ", additionalInfo=" + additionalInfo + ", movieyear=" + movieyear
				+ ", nominee=" + nominee + ", boxOffice=" + boxOffice + ", category=" + category + ", oscarWinner="
				+ oscarWinner + ", imdbVotes=" + imdbVotes + ", imdbRating=" + imdbRating + "]";
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the additionalInfo
	 */
	public String getAdditionalInfo() {
		return additionalInfo;
	}

	/**
	 * @param additionalInfo the additionalInfo to set
	 */
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	/**
	 * @return the movieyear
	 */
	public String getMovieyear() {
		return movieyear;
	}

	/**
	 * @param movieyear the movieyear to set
	 */
	public void setMovieyear(String movieyear) {
		this.movieyear = movieyear;
	}

	/**
	 * @return the rating
	 */
	public Double getImdbRating() {
		return imdbRating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setImdbRating(Double imdbRating) {
		this.imdbRating = imdbRating;
	}

	/**
	 * @return the nominee
	 */
	public String getNominee() {
		return nominee;
	}

	/**
	 * @param nominee the nominee to set
	 */
	public void setNominee(String nominee) {
		this.nominee = nominee;
	}

	/**
	 * @return the box_Office
	 */
	public BigDecimal getBoxOffice() {
		return boxOffice;
	}

	/**
	 * @param box_Office the box_Office to set
	 */
	public void setBoxOffice(BigDecimal boxOffice) {
		this.boxOffice = boxOffice;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the oscar_Winner
	 */
	public boolean isOscarWinner() {
		return oscarWinner;
	}

	/**
	 * @param oscar_Winner the oscar_Winner to set
	 */
	public void setOscarWinner(boolean oscarWinner) {
		this.oscarWinner = oscarWinner;
	}

	/**
	 * @return the no_of_ratings
	 */
	public Integer getImdbVotes() {
		return imdbVotes;
	}

	/**
	 * @param no_of_ratings the no_of_ratings to set
	 */
	public void setImdbVotes(Integer imdbVotes) {
		this.imdbVotes = imdbVotes;
	}

}