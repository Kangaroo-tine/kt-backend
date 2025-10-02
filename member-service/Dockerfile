FROM eclipse-temurin:17-jre
ENV TZ=Asia/Seoul
WORKDIR /app

COPY build/libs/member-service.jar /app/app.jar

EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]
