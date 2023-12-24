# lock-concurrent

- Java Thread에 대해 이해
- Non-Repeatable Read, Phantom Read 동시성 문제 확인
- JPA와 Spring Data JPA의 ISOLATION LEVEL을 이용한 동시성 문제 해결

---

### 실험 준비

1. docker demon 실행
2. `docker-compose up -d` 로 DB 만들기
3.  원하는 테스트 실행시키기

--- 

### 비관적락 실험



