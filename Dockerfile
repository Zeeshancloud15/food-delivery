FROM openjdk:17

WORKDIR /app

COPY target/java-maven-app-1.0.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
