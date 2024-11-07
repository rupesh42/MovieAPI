package com.rupesh.assignment.movieapplication.domain;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This class is simply a DTO for Entity for loose coupling of Entity and better maintainability.
 * @author Rupesh
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MovieDTO {
    private Integer id;
    private String additionalInfo;
    private String movieYear;
    private String nominee;
    private BigDecimal boxOffice;
    private String category;
    private boolean oscarWinner;
    private Integer imdbVotes;
    private Double imdbRating;
    
}
