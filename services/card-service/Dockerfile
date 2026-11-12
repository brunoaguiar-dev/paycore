FROM eclipse-temurin:25-jdk AS build
WORKDIR /workspace

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY platform/spring-parent/pom.xml platform/spring-parent/
COPY services/card-service/pom.xml services/card-service/
RUN ./mvnw -B -pl services/card-service -am dependency:go-offline

COPY services/card-service/src services/card-service/src
RUN ./mvnw -B -pl services/card-service -am package -DskipTests

FROM eclipse-temurin:25-jre
WORKDIR /app

RUN useradd --system --uid 1001 paycore
USER paycore

COPY --from=build /workspace/services/card-service/target/card-service-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
