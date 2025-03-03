FROM openjdk:12-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG JAR_FILE=target/*.jar 
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Xmx521m", "-Dserver.port=${PORT}" ,"-jar","/app.jar"]