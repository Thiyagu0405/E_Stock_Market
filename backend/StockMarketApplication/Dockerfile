FROM openjdk:8
EXPOSE 8080
ADD target/spring-boot-docker-aws.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]