# Java 21 버전을 기반으로 하는 베이스 이미지
FROM openjdk:21-jdk-slim

# 컨테이너 내부의 작업 디렉토리 설정
WORKDIR /app

# JAR 파일을 컨테이너 내부로 복사
COPY build/libs/HonorsParkingBE-0.0.1-SNAPSHOT.jar app.jar

# JAR 파일 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
