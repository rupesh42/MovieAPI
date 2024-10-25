#  Solution

As per the provided assessment:

**Design Decisions:**

**Spring Boot Framework**: To implement REST APIs, Springboot is used
**Hibernate**: Used for ORM to interact with the MySQL database.
**CSV Parsing**: Data loaded from the CSV file to mysql.
**Design Pattern**: Command pattern is used to build this solution where each operation is decoupled with each other

Below are the operation are included in the solution

1. **Check if a Movie won Oscar or not**:
Find a movie in the database and filter if the Oscar winner column is tre or false, return a statement if the oscar winner is true. 

2. **Provide rating to a movie**:
Find a movie with it's name in the database and apply new rating (using average) and increment the votes by 1. If the movie's IMDB Votes and rating is not already present, this solution will hit API to fetch the boxxOfice value, IMdB votes and rating and will store in our Database.

3. **Fetch 10 top-rated movies based on Box Office value**:
Query top 10 rated movies and order it by boxOffice value in the Database.

**Explanation:**

- MovieEntity: Represents the movie data in the database.
- MovieDTO: Data Transfer Object for movie data.
- MovieRepository: Manages database operations for MovieEntity.
- MovieDataUpdater: Loads initial data from CSV and updates movie data.
- MovieService: Contains business logic for updating ratings, fetching movies by nominee, getting the top 10 movies, and checking Best Picture winners.
- MovieController: Handles HTTP requests and routes them to the MovieService.

**Relationships:**
- MovieEntity and MovieDTO: Aggregation (MovieDTO is a part of the MovieEntity).
- MovieRepository and MovieEntity: Association (MovieRepository interacts with MovieEntity).
- OMDbApiClient and MovieEntity: Association (OMDbApiClient fetches data for MovieEntity).
- MovieDataUpdater and MovieEntity: Association (MovieDataUpdater updates MovieEntity data).
- MovieService and MovieRepository, OMDbApiClient, MovieDataUpdater: Association (MovieService uses these components).
- MovieController and MovieService: Association (MovieController interacts with MovieService).
