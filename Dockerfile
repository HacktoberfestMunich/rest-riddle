FROM openjdk:16-alpine

LABEL org.opencontainers.image.source="https://github.com/HacktoberfestMunich/rest-riddle"
LABEL org.opencontainers.image.url="https://github.com/HacktoberfestMunich/rest-riddle"

ENTRYPOINT ["java", "-jar", "/app/rest-riddle.jar"]
ADD "build/libs/rest-riddle-*.jar" /app/rest-riddle.jar
