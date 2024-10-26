package com.rupesh.assignment.movieapplication.domain;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * This class is simply a DTO for Entity for loose coupling of Entity and better maintainability.
 * @author Rupesh
 *
 */
public class MovieDTO {
    private Integer id;
    private String additionalInfo;
    private String movieyear;
    private String nominee;
    private BigDecimal boxOffice;
    private String category;
    private boolean oscarWinner;
    private Integer imdbVotes;
    private Double imdbRating;
    
	public MovieDTO() {
		super();
	}
	
	public MovieDTO(Integer id, String additionalInfo, String movieyear, String nominee, BigDecimal boxOffice,
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
	public int hashCode() {
		return Objects.hash(additionalInfo, boxOffice, category, id, imdbRating, imdbVotes, movieyear, nominee,
				oscarWinner);
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof MovieDTO)) {
			return false;
		}
		MovieDTO other = (MovieDTO) obj;
		return Objects.equals(additionalInfo, other.additionalInfo) && Objects.equals(boxOffice, other.boxOffice)
				&& Objects.equals(category, other.category) && Objects.equals(id, other.id)
				&& Objects.equals(imdbRating, other.imdbRating) && Objects.equals(imdbVotes, other.imdbVotes)
				&& Objects.equals(movieyear, other.movieyear) && Objects.equals(nominee, other.nominee)
				&& oscarWinner == other.oscarWinner;
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
	 * @return the boxOffice
	 */
	public BigDecimal getBoxOffice() {
		return boxOffice;
	}
	/**
	 * @param boxOffice the boxOffice to set
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
	 * @return the oscarWinner
	 */
	public boolean isOscarWinner() {
		return oscarWinner;
	}
	/**
	 * @param oscarWinner the oscarWinner to set
	 */
	public void setOscarWinner(boolean oscarWinner) {
		this.oscarWinner = oscarWinner;
	}
	/**
	 * @return the imdbVotes
	 */
	public Integer getImdbVotes() {
		return imdbVotes;
	}
	/**
	 * @param imdbVotes the imdbVotes to set
	 */
	public void setImdbVotes(Integer imdbVotes) {
		this.imdbVotes = imdbVotes;
	}
	/**
	 * @return the imdbRating
	 */
	public Double getImdbRating() {
		return imdbRating;
	}
	/**
	 * @param imdbRating the imdbRating to set
	 */
	public void setImdbRating(Double imdbRating) {
		this.imdbRating = imdbRating;
	}

    
}
