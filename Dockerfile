FROM openjdk:8-jdk-alpine
VOLUME /tmp
ADD target/real-estate-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENV http_proxy 8.8.8.8:/4000
# Make port 80 available to the world outside this container
EXPOSE 80
ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar