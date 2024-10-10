FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/Aerolinea-0.0.1-SNAPSHOT.jar Aerolinea.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "Aerolinea.jar"]