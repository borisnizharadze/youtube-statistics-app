# Use an official openjdk image with Java 11 as the base image
FROM openjdk:11

# Set the working directory to /producer
WORKDIR /producer

# Copy the JAR file to the container
COPY target/*.jar producer.jar


# Run the command to start the Spring Boot application
CMD ["java", "-jar", "producer.jar"]
