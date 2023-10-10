FROM openjdk:8-jdk-alpine
EXPOSE 8083
ADD target/5TWIN5-OneZero-SkiStation.jar 5TWIN5-OneZero-SkiStation.jar
ENTRYPOINT ["java","-jar","/docker-spring-boot.war"]