FROM azul/zulu-openjdk-alpine:21-latest
WORKDIR /app
COPY build.gradle gradlew gradlew.bat ./
COPY gradle ./gradle
RUN ./gradlew dependencies --refresh-dependencies --no-daemon || true
COPY . .
RUN ./gradlew bootJar --no-daemon
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "build/libs/Unichess-Backend-0.0.1-SNAPSHOT.jar"]