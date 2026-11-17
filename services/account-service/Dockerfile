FROM eclipse-temurin:25-jdk AS build
WORKDIR /workspace

COPY mvnw .
COPY .mvn .mvn
COPY platform/spring-parent/pom.xml platform/spring-parent/
COPY services/account-service/pom.xml services/account-service/
RUN ./mvnw -B -f services/account-service/pom.xml dependency:go-offline

COPY services/account-service/src services/account-service/src
RUN ./mvnw -B -f services/account-service/pom.xml package -DskipTests

FROM eclipse-temurin:25-jre
WORKDIR /app

RUN useradd --system --uid 1001 paycore
USER paycore

COPY --from=build /workspace/services/account-service/target/account-service-*.jar app.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]