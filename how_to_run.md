# Movie API, How to Run.

## Overview
The Movie API allows users to interact with a comprehensive movie database. Users can update movie ratings, find movies by nominee, retrieve top movies, and get information about Best Picture winners.

### Setup Instructions

#### Prerequisites
- Java 21
- Maven
- Spring Boot

#### Installation
**Clone the repository:**
   
	git clone https://github.com/yourusername/movie-api.git	
	cd movie-api

**Build the project:**
   
	mvn clean install

**Run the application**
	
	mvn spring-boot:run
	
**Configuration**

	server.port=8081
	csv.file.path=classpath:encoded-academy_awards.csv

**Testing an Application.**
	
	mvn test

**Exception Handling**

-  The API handles various exceptions such as ResourceNotFoundException and CustomException to provide meaningful error messages to the users.

**Logging**

-  The application uses SLF4J for logging. Logs are written to the console and can be configured to be written to files as well.

**Testing**

- Unit tests are written using JUnit and Mockito. Ensure all tests pass before deploying the application.	


