package com.rupesh.assignment.movieapplication.utils;

/**
 * Enum representing various categories of movies.
 */
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
	 * Constructor to set the description for the movie category.
	 * 
	 * @param description the description of the category
	 */
	OscarCategory(String description) {
		this.description = description;
	}

	/**
	 * Gets the description of the movie category.
	 * 
	 * @return the description of the category
	 */
	public String getDescription() {
		return description;
	}

}
