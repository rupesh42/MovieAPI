package com.rupesh.assignment.movieapplication.domain;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * This class is used when we are trying to fetch IMDB and BoxOffice from the API. 
 * Hence the variables. 
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieData {

	@JsonDeserialize(using = CustomBigDecimalDeserializer.class)
	private BigDecimal BoxOffice;

	@JsonDeserialize(using = DoubleDeserializer.class)
	private Double imdbRating;

	private String imdbVotes;

	/**
	 * @return the boxOffice
	 */
	public BigDecimal getBoxOffice() {
		return BoxOffice;
	}

	/**
	 * @param boxOffice the boxOffice to set
	 */
	public void setBoxOffice(BigDecimal BoxOffice) {
		this.BoxOffice = BoxOffice;
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

	/**
	 * @return the imdbVotes
	 */
	public String getImdbVotes() {
		return imdbVotes;
	}

	/**
	 * @param imdbVotes the imdbVotes to set
	 */
	public void setImdbVotes(String imdbVotes) {
		this.imdbVotes = imdbVotes;
	}

	public static class DoubleDeserializer extends JsonDeserializer<Double> {

		@Override
		public Double deserialize(JsonParser p, DeserializationContext ctxt)
				throws IOException {
			String value = p.getText();
			try {
				return Double.valueOf(value);
			} catch (NumberFormatException e) {
				return null;
				// or any default value you want touse
			}
		}

	}

	/**
	 * This method clean the boxoffice value
	 * For example: $2,344,565.55 will be 2344565.55 for ease of maintaining.
	 */
	public static class CustomBigDecimalDeserializer extends JsonDeserializer<BigDecimal> {
		@Override
		public BigDecimal deserialize(JsonParser p, DeserializationContext ctxt)
				throws IOException {
			String value = p.getText();
			try {
				// Remove any commas and dollar signs and convert to BigDecimal
				return new BigDecimal(value.replaceAll("[^\\d.]", ""));
			} catch (NumberFormatException e) {
				return null; // or any default value
			}
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(BoxOffice, imdbRating, imdbVotes);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof MovieData)) {
			return false;
		}
		MovieData other = (MovieData) obj;
		return Objects.equals(BoxOffice, other.BoxOffice) && Objects.equals(imdbRating, other.imdbRating)
				&& Objects.equals(imdbVotes, other.imdbVotes);
	}

	@Override
	public String toString() {
		return "MovieData [BoxOffice=" + BoxOffice + ", imdbRating=" + imdbRating + ", imdbVotes=" + imdbVotes + "]";
	}

}