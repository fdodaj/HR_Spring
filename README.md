
# Human Resources App - Spring Boot

This is a simple human resources project made with java and spring boot framework.                                                                          
Project made with intuit to learn java spring boot framework

## Features

* *JWT for token creation and validation.* 
* *Role based authorization..* 
* *OpenApi and Swagger integration..* 
* *Automated  version-based database migrations using Flyway.* 
 
## Security

Integration with Spring Security and add other filter for jwt token process.

The secret key is stored in application.yml.


## Database

It uses a H2 in memory database (for now), can be changed easily in the application.yml for any other database.

## Running the  app
You need  [Java](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html) and [Maven](https://maven.apache.org/download.cgi)  installed 

1. Build Spring Boot Project with Maven
  ```
      mvn package
```
2. Run Spring Boot app using Maven:
  ```
      mvn spring-boot:run
```

3. [optional] Run Spring Boot app with java -jar command
  ```
      java -jar target/hr-management-0.0.1-SNAPSHOT.jar
```

4. Go to http://localhost:8080/swagger-ui.html 

5. Log in as admin, employee, pd(department leader) or hr

```
      {
        "email": "[roleName]@gmail.com",
        "password": "password"
      }
      
```

## Tech Stack used in the project:

* Java 11
* SpringBoot
* Spring MVC
* Spring Security
* Spring Data JPA
* H2 database
* Lombok
* Open API
* Flyway

     
     
  

