FROM eclipse-temurin:17-jre
ENV TZ=Asia/Seoul
WORKDIR /app

COPY build/libs/planner-service.jar /app/app.jar

EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]
