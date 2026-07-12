FROM eclipse-temurin:21-jdk

WORKDIR /appContainer

COPY ./target/spring-docker-jenkins.jar .

CMD ["java","-jar","spring-docker-jenkins.jar"]