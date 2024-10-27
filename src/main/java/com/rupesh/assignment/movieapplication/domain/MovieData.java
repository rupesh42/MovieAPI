package com.rupesh.assignment.movieapplication.domain;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * This class is used when we are trying to fetch IMDB and BoxOffice from the API. 
 * Hence the variables. 
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
@EqualsAndHashCode
public class MovieData {

	@JsonDeserialize(using = CustomBigDecimalDeserializer.class)
	private BigDecimal BoxOffice;

	@JsonDeserialize(using = DoubleDeserializer.class)
	private Double imdbRating;

	private String imdbVotes;

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


}