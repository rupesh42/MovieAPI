package com.rupesh.assignment.movieapplication.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This is ErrorMessage which will be useful in case of errors returning statusCode, timestamp, message and description
 * @author Rupesh
 *
 */
@Data
@AllArgsConstructor
public class ErrorMessage {
  private int statusCode;
  private Date timestamp;
  private String message;
  private String description;

}
