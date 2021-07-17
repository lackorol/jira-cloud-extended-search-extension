FROM adoptopenjdk/openjdk15:alpine-jre
WORKDIR one-extension
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]