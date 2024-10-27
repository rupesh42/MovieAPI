package com.rupesh.assignment.movieapplication.utils;

import lombok.AllArgsConstructor;

/**
 * Enum representing various categories of movies.
 */
@AllArgsConstructor
public enum OscarCategory {

	/** Category for feature films. */
	MOVIE("Movie Category"),

	/** Category for actors' awards. */
	ACTORS("Actors Category"),

	/** Category for technical awards. */
	TECHNICAL("Technical Awards"),

	/** Category for other miscellaneous awards. */
	OTHER("Other awards"),

	/** Category for documentary films. */
	DOCUMENTARY("Documentary"),

	/** Category for short films. */
	SHORT("Short");

	private final String description;

	/**
	 * Gets the description of the movie category.
	 * 
	 * @return the description of the category
	 */
	public String getDescription() {
		return description;
	}

}
