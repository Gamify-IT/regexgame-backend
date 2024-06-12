# Regexgame-Backend

This repository contains the backend for
the [regexgame minigame](https://gamifyit-docs.readthedocs.io/en/latest/user-manuals/minigames/regexgame.html).

It persists the game data (configurations, game results, etc.) in a database and communicates with other backend
services.

## Table of contents

<!-- TOC -->
* [Links](#links)
* [REST API](#rest-api)s
  * [Swagger-Ui](#swagger-ui)
* [Getting started](#getting-started)
* [Run](#run)
  * [Project build](#project-build)
  * [Build with docker](#build-with-docker)
  * [Docker compose](#docker-compose)
* [Testing Database](#testing-database)
<!-- TOC -->

## Links

- User documentation for the minigame can be
  found [here](https://gamifyit-docs.readthedocs.io/en/latest/user-manuals/minigames/regexgame.html).
- For the frontend, see the [Gamify-IT/regexgame repository](https://github.com/Gamify-IT/regexgame).
- The installation manual and setup instructions can be
  found [here](https://gamifyit-docs.readthedocs.io/en/latest/install-manuals/index.html).
- Docker documentation can be found Check the [here](https://github.com/Gamify-IT/docs/blob/main/dev-manuals/languages/docker/docker-compose.md).

## REST API

Rest mappings are defined in

- Game result
  controller: [`src/main/java/de/unistuttgart/regexgamebackend/controller/GameResultController.java`](src/main/java/de/unistuttgart/regexgamebackend/controller/GameResultController.java)
- Config
  controller: [`src/main/java/de/unistuttgart/regexgamebackend/controller/ConfigController.java`](src/main/java/de/unistuttgart/regexgamebackend/controller/ConfigController.java)

### Swagger-Ui

When the service is started (see [Getting started](#getting-started)), you can access the API documentation:

Open <http://localhost/minigames/regexgame/api/v1/swagger-ui/index.html#/> and
fill `http://localhost/minigames/regexgame/api/v1/v3/api-docs` into the input field in the navbar.


## Getting started

Make sure you have the following installed:

- Java: [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or higher
- Maven: [Maven 3.6.3](https://maven.apache.org/download.cgi)
- Docker: [Docker latest](https://www.docker.com/)

First you have to change the spring.datasource.username and the spring.datasource.password in the application.properties
file. If you changed the properties of the postgres db, you also have to change spring.datasource.url.

## Run
### Project build

Execute the following in the project directory to build the project locally:
```sh
mvn install
```

Then go to the target folder and run:

```sh
java -jar regexgame-service-0.0.1-SNAPSHOT.jar
```

### Build with Docker

Build the Docker container with the following:

```sh
docker build  -t regexgame-backend-dev .
```

And run it at port 8000 with:

```sh
docker run -d -p 8000:80 -e POSTGRES_URL="postgresql://host.docker.internal:5432/postgres" -e POSTGRES_USER="postgres" -e POSTGRES_PASSWORD="postgres" --name regexgame-backend-dev regexgame-backend-dev
```

To monitor, stop and remove the container you can use the following commands:

```sh
docker ps -a -f name=regexgame-backend-dev
```

```sh
docker stop regexgame-backend-dev
```

```sh
docker rm regexgame-backend-dev
```

To run the prebuild container use:

```sh
docker run -d -p 8000:80 -e POSTGRES_URL="postgresql://host.docker.internal:5432/postgres" -e POSTGRES_USER="postgres" -e POSTGRES_PASSWORD="postgres" --name regexgame-backend ghcr.io/gamify-it/regexgame-backend:latest
```

### Docker compose
To use the docker compose files to run your local changes as a container, use:
```sh
docker compose up --build
```
To shutdown the container use:
```sh
docker compose down
```

If you only want to start the dependencies via docker and run the project locally, use the `docker-compose-dev.yaml`
file instead:
```sh
docker compose -f docker-compose-dev.yaml up
```
To shutdown use:
```sh
docker compose -f docker-compose-dev.yaml down
```
You can then run the backend locally as described above.

## Testing Database

To setup a database with docker for testing you can use:

```sh
docker run -d -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=postgres  --rm --name regexgame-database postgres
```

To stop and remove it use:

```sh
docker stop regexgame-database
```