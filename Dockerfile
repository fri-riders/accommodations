FROM openjdk:8-jre-alpine
RUN mkdir /app
WORKDIR /app
ADD target/real-estate-0.0.1-SNAPSHOT.jar /app/app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "app.jar"]