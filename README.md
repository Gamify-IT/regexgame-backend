# Finitequiz-Backend

This repo serves to persist the finitequiz data in a db and to communicate with different Microservices.

<!-- TOC -->

* [Getting started](#getting-started)
    * [Run](#run)
        * [Docker-compose](#docker-compose)
        * [Project build](#project-build)
        * [With Docker](#with-docker)
    * [Testing Database](#testing-database)
* [Rest mappings](#rest-mappings)
    * [Swagger-Ui (if started)](#swagger-ui--if-started-)
* [Class Diagrams](#class-diagrams)

<!-- TOC -->

## Getting started

Make sure you have the following installed:

- Java: [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or higher
- Maven: [Maven 3.6.3](https://maven.apache.org/download.cgi)
- Docker: [Docker latest or higher](https://www.docker.com/)

First you have to change the spring.datasource.username and the spring.datasource.password in the application.properties
file. If you changed the properties of the postgres db, you also have to change spring.datasource.url.

### Run

#### Docker-compose

Start all dependencies with our docker-compose files.
Check
the [manual for starting the dependencies with docker-compose](https://github.com/Gamify-IT/docs/blob/main/dev-manuals/languages/docker/docker-compose.md)
.

#### Project build

```sh
mvn install
```

in the folder of the project.
Go to the target folder and run

```sh
java -jar finitequiz-service-0.0.1-SNAPSHOT.jar
```

#### With Docker

Build the Docker container with

```sh
docker build  -t finitequiz-backend-dev .
```

And run it at port 8000 with

```
docker run -d -p 8000:80 -e POSTGRES_URL="postgresql://host.docker.internal:5432/postgres" -e POSTGRES_USER="postgres" -e POSTGRES_PASSWORD="postgres" --name finitequiz-backend-dev finitequiz-backend-dev
```

To monitor, stop and remove the container you can use the following commands:

```sh
docker ps -a -f name=finitequiz-backend-dev
```

```sh
docker stop finitequiz-backend-dev
```

```sh
docker rm finitequiz-backend-dev
```

To run the prebuild container use

```sh
docker run -d -p 8000:80 -e POSTGRES_URL="postgresql://host.docker.internal:5432/postgres" -e POSTGRES_USER="postgres" -e POSTGRES_PASSWORD="postgres" --name finitequiz-backend ghcr.io/gamify-it/finitequiz-backend:latest
```

### Testing Database

to setup a database with docker for testing you can use

```sh
docker run -d -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=postgres  --rm --name finitequiz-database postgres
```

To stop and remove it simply type

```sh
docker stop finitequiz-database
```

## Rest mappings

Rest mappings are defined
in [`finitequiz-service/src/main/java/com/finitequizservice/finitequizservice/controller/FinitequizController.java`](finitequiz-service/src/main/java/com/finitequizservice/finitequizservice/controller/FinitequizController.java)

### Swagger-Ui (if started)

Access swagger-ui for visuals under: ```http://localhost/minigames/finitequiz/api/v1/swagger-ui/index.html#/``` and
fill ```http://localhost/minigames/finitequiz/api/v1/v3/api-docs``` into the input field in the navbar.
![img.png](assets/finitequiz-swagger.png)

## Class Diagrams

![ConfigController](assets/ConfigService.svg)

![GameResultController](assets/GameResult.svg)