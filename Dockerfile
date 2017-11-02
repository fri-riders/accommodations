FROM openjdk:8-jre-alpine
EXPOSE 8080
ADD target/accommodations-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]