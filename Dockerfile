FROM openjdk:8-jre-alpine
EXPOSE 8081
ENV CONFIG_SERVER_URL consul
ENV DISCOVERY_HOSTNAME accommodations
ADD target/accommodations-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]