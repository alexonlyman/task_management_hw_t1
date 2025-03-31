FROM openjdk:17
WORKDIR /app
EXPOSE 8080
COPY target/task_management_hw_t1-0.0.1-SNAPSHOT.jar /app/task_management_hw_t1-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/task_management_hw_t1-0.0.1-SNAPSHOT.jar"]
