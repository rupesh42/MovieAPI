package com.rupesh.assignment.movieapplication.utils;

import lombok.NoArgsConstructor;

/**
 * This class will help current and future implementation If developer needs to
 * perform any action based on the category of the movie this Category Mapper
 * can be used. Feel free to add or change the categories but mind the change in
 * service layers if you are changing the Movie category.
 * 
 * @author Rupesh
 *
 */
@NoArgsConstructor
public class OscarCategoryMapper {

	public static OscarCategory getCategory(String awardType) {
		switch (awardType) {
		
		case "Art Direction", "Cinematography", "Costume Design", "Film Editing", "Directing", "Sound", "Sound Editing",
				"Visual Effects", "Writing", "Makeup", "Music (Scoring)":
			return OscarCategory.TECHNICAL;
		
		case "Foreign Language Film", "Animated Feature Film", "Best Picture":
			return OscarCategory.MOVIE;
		
		case "Actor -- Leading Role","Actor -- Supporting Role","Actress -- Leading Role","Actress -- Supporting Role":
			return OscarCategory.ACTORS;
		
		case "Documentary (Feature)","Documentary (Short Subject)":
			return OscarCategory.DOCUMENTARY;
		
		case "Short Film (Animated)","Short Film (Live Action)":
			return OscarCategory.SHORT;
		
		default:
			return OscarCategory.OTHER;
		}
	}
}
