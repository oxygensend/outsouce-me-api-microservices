FROM openjdk:21-jdk

ARG PORT=8761

ENV TZ=Europe/Warsaw

HEALTHCHECK --interval=5s --timeout=5s --retries=10 CMD curl -f http://localhost:$PORT/manage/health || exit 1

COPY target/eureka.jar  /app/eureka.jar

WORKDIR /app

ENTRYPOINT exec java $JAVA_OPTS -jar /app/*.jar

EXPOSE $PORT

