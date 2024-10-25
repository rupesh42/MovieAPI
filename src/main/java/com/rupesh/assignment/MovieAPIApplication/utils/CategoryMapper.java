package com.rupesh.assignment.MovieAPIApplication.utils;


/**
 * This class will help current and future implementation 
 * If developer needs to perform any action based on the category of the movie this Category Mapper can be used.
 * Feel free to add or change the categories but mind the change in service layers if you are changing the Movie category. 
 * @author Rupesh
 *
 */
public class CategoryMapper {

    public static MovieCategory getCategory(String awardType) {
        switch (awardType) {
            case "Art Direction":
            case "Cinematography":
            case "Costume Design":
            case "Film Editing":
            case "Directing":
            case "Sound":
            case "Sound Editing":
            case "Visual Effects":
            case "Writing":
            case "Makeup":
            case "Music (Scoring)":
            	return MovieCategory.TECHNICAL;
            case "Foreign Language Film":
            case "Animated Feature Film":
            case "Best Picture":
                return MovieCategory.MOVIE;
            case "Actor -- Leading Role":
            case "Actor -- Supporting Role":
            case "Actress -- Leading Role":
            case "Actress -- Supporting Role":
                return MovieCategory.ACTORS;
            case "Documentary (Feature)":
            case "Documentary (Short Subject)":
            return MovieCategory.DOCUMENTARY;
            case "Short Film (Animated)":
            case "Short Film (Live Action)":
            	return MovieCategory.SHORT;
            default:
            	return MovieCategory.OTHER;
        }
    }
}
