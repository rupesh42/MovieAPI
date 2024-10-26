# Movie API

This is a movie API to rate a movie, find top 10 movies, and to find if the movie has won best picture or not.


### Requirement

#### Functional Requirements
    
Overview:
The goal of the technical test is to create a Java web application.
The application should Indicate whether a movie won a “Best Picture” Oscar, given a movie’s title based on this API and this CSV file that
contains winners from 1927 until 2010. It should also allow users to give a rating to movies and provide a list of 10 top-rated movies ordered by
box office value.

#### Technical landscape.
So, I have fetched all the data from CSV and loaded it in the Movie database, and fetched the IMdB ratings, BoxOffice value and votes of the movies which have been nominated for Best Picture, Animated feature film and Foreign Language Film, which are approx 800. I had limitations of hitting only 1000 requests per day on based on the free API key, hence only these three categories are choosen.
But whenever user tries to rate a movie which is not from these category, this solution will hit API to fetch the boxxOfice value, IMdB votes and rating and will store in our Database.  

The Movie API has the following functional requirements:
#####  You can maintain rate a movie:
- Movie Name
- Your rating

#####  You can find top 10 rated movies ordered by BoxOffice value:
- No Parameters required

#####  You can find if a movie has won best picture or not:
- Movie Name

## TL;DR - Build and Run
 
	 mvn clean install
	 
## Code Quality Factors that are taken care:

- Total 62.8% code coverage by Junit
- Using SonarLint all "Major" issues are resolved. 	 
- No external library from untrusted source dependency is used.
- Code smell is as low as possible

## SWAGGER URL:
	http://localhost:8081/swagger-ui/index.html

## Flowchart:
![path to flowchart](https://github.com/rupesh42/MovieAPI/blob/main/src/main/resources/flowChart.PNG)

## Next Steps / Improvement areas
##### - I could hit API and fetch more data if I didn't have limitations of 1000 hits per day.
##### - In Many cases cleaning Movie name is very difficult, moreover, the names of foreign movies are different in API vs in CSV file, i.e, The API has english names and the CSV files have original language names. That can be fixed using more cleaner data cleaning mechanism. 
##### - Security can be improved.
##### - Containerization.