FROM openjdk:11-jre-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} work-17.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","/work-17.jar"]