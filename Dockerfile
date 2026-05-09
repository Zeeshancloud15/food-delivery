FROM openjdk:17-jdk

WORKDIR /app

COPY target/foodapp-1.0.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]
