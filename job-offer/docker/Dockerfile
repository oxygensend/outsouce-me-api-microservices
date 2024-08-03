FROM openjdk:21-jdk

ARG CONFIG=config/config-docker.yml
ARG PORT=9999

ENV TZ=Europe/Warsaw

HEALTHCHECK --interval=5s --timeout=5s --retries=10 CMD curl -f http://localhost:$PORT/manage/health || exit 1

COPY $CONFIG /app/config/config.yml
COPY target/job-offer.jar  /app/job-offer.jar


WORKDIR /app

ENTRYPOINT exec java $JAVA_OPTS --add-opens java.base/java.lang=ALL-UNNAMED -jar /app/*.jar --spring.config.additional-location=config/config.yml

EXPOSE $PORT

