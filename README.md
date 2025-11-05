# kafka-playground

## 🌏 프로젝트 실행 방법

이 프로젝트는 Spring Boot와 Kafka를 사용하여 메시지를 주고받는 간단한 예제입니다.

### ✅ 설치

- [Docker Desktop](https://www.docker.com/products/docker-desktop/)

### ✅ 실행 순서

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

### ✅ 테스트 방법

애플리케이션 실행이 완료되면, 새 터미널에서 아래 `curl` 명령어를 실행하여 회원가입을 요청하고 이벤트를 발생시킬 수 있습니다.

```bash
curl -X POST -H "Content-Type: application/json" \
-d '{"id": "testuser", "password": "password123"}' \
http://localhost:8080/register
```

-   **요청 결과:** `User registered successfully!` 메시지가 보입니다.
-   **애플리케이션 로그:** Spring Boot 애플리케이션이 실행된 터미널에 `Published user-registered event for: testuser` 로그가 출력됩니다.
