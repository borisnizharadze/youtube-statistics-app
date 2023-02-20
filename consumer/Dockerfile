# Use an official openjdk image with Java 11 as the base image
FROM openjdk:11

# Set the working directory to /producer
WORKDIR /consumer

# Copy the JAR file to the container
COPY target/*.jar consumer.jar


# Run the command to start the Spring Boot application
CMD ["java", "-jar", "consumer.jar"]
