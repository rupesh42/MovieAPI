package com.rupesh.assignment.movieapplication.exception;

/**
 * Tis class will be used to throw custom made exception in cases of error
 * @author Rupesh
 *
 */
public class MovieAPIException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MovieAPIException(String message) {
		super(message);
	}

}
