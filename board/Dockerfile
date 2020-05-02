FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
LABEL maintainer="devcrema@gmail.com"
VOLUME /tmp
EXPOSE 20000
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} board.jar
ENTRYPOINT ["java","-jar","-Djava.security.egd=file:/dev/./urandom","/board.jar"]

#docker build -t spring-boot-board
#docker run -e "SPRING_PROFILES_ACTIVE=local" -p 20000:18080 -t spring-boot-board