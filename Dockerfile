FROM openjdk:11-jdk-slim
EXPOSE 8089
ADD target/tp-foyer-5.0.4.jar tp-foyer.jar
ENTRYPOINT ["java", "-jar", "/tp-foyer.jar"]
