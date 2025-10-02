FROM eclipse-temurin:17-jre
ENV TZ=Asia/Seoul
WORKDIR /app

COPY build/libs/api-gateway.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
