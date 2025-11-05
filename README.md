# kafka-playground

# 🌏 프로젝트 아키텍처

이 프로젝트는 Spring Boot 애플리케이션과 Docker를 통해 실행되는 Kafka가 메시지를 통해 통신하는 구조입니다.

```
+--------------------------------+
|                                |
|      Spring Boot 서버 (내 PC)    |
|                                |
|  [ UserController ]            |
|   - 회원가입 API (/register)     |
|   - KafkaTemplate으로 이벤트 발행 |
|                                |
+--------------------------------+
             |
             | 1. 'user-registered' 이벤트 전송 (JSON)
             |
             v
+--------------------------------+
|                                |
|      Docker 컨테이너 (가상 환경) |
|                                |
|  [ Kafka ]                     |
|   - 'user-registered' 토픽에    |
|     이벤트 저장                  |
|                                |
+--------------------------------+
```

1.  **Spring Boot 서버**: 사용자의 회원가입 요청을 받아 처리하고, `KafkaTemplate`을 이용해 `user-registered` 이벤트를 Kafka로 전송하는 **생산자(Producer)** 역할을 합니다.
2.  **Kafka 컨테이너**: Spring Boot 서버로부터 받은 이벤트를 `user-registered` 토픽에 안전하게 저장하는 **메시지 브로커(Broker)** 역할을 합니다. 이 컨테이너는 Docker를 통해 독립된 환경에서 실행됩니다.

# 🌏 프로젝트 실행 방법

이 프로젝트는 Spring Boot와 Kafka를 사용하여 메시지를 주고받는 간단한 예제입니다.

## ✅ 설치

- [Docker Desktop](https://www.docker.com/products/docker-desktop/)

## ✅ 실행 순서

1.  **Kafka 실행**

    프로젝트 루트 경로에서 아래 명령어를 터미널에 입력하여 Kafka와 Zookeeper를 실행합니다.
    ```bash
    docker compose up -d
    ```

2.  **Spring Boot 애플리케이션 실행**

    새 터미널을 열고, 아래 명령어를 입력하여 Spring Boot 애플리케이션을 실행합니다.
    ```bash
    ./gradlew bootRun
    ```

## ✅ 테스트 방법

애플리케이션 실행이 완료되면, 새 터미널에서 아래 `curl` 명령어를 실행하여 회원가입을 요청하고 이벤트를 발생시킬 수 있습니다.

```bash
curl -X POST -H "Content-Type: application/json" \
-d '{"id": "testuser", "password": "password123"}' \
http://localhost:8080/register
```

-   **요청 결과:** `User registered successfully!` 메시지가 보입니다.
-   **애플리케이션 로그:** Spring Boot 애플리케이션이 실행된 터미널에 `Published user-registered event for: testuser` 로그가 출력됩니다.
