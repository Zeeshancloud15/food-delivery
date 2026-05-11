FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY target/java-maven-app-1.0.jar app.jar

EXPOSE 5000

CMD ["java", "-jar", "app.jar"]
