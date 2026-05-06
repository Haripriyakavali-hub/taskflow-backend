FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
COPY render-start.sh render-start.sh
RUN chmod +x render-start.sh
EXPOSE 10000
ENTRYPOINT ["./render-start.sh"]
