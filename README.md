# Rest-Riddle

This is a small webserver which serves the payloads and files for the Hacktoberfest 2021 event of the Hacktoberfest Munich Meetup.

The goal is to document the whole behaviour of the REST api in a separate GitHub project and open PRs with new findings.

## Compile / Run

To compile it from terminal just type the following in the project folder. This will create an artefact under `build/libs/`.

```shell
./gradlew bootJar
```

To start the project via terminal type:

```shell
./gradlew bootRun
```

The project can of course also be opened with any IDE, for myself I use IntelliJ IDEA.

## Run via docker

To run the riddles via a docker image, we provide images for each version in th packages of this repository.

To start up the riddle server on port 8080 in docker use:

```shell
docker run -p 8080:8080 ghcr.io/hacktoberfestmunich/rest-riddle:latest
```

## Challenge overview

To see the flow of the current challenges look at [the draw.io diagram](./challenges.drawio.png)
