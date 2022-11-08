FROM openjdk:8
EXPOSE 9090
ADD target/spring-zuul-aws-api.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]