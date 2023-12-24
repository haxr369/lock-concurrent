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

`src/test/java/com/solsol/lock/lock/IsolationLevelTest.java`
위 파일의 `IsolationTest`의 3가지 테스트를 진행

1. `givenSingleThreadAndTransaction_whenUpdated_thenSuccess`
    - 싱글 스레드를 이용해서 티켓 수량 1개 감소 시키기.
    - 메인 스레드에서 1개의 티켓만 업데이트하니까 성공
2. `givenMultiThreadAndTransaction_whenUpdated_thenFail`
   - 20개의 멀티 스레드를 이용해서 티켓 수량 100개 감소 시키기.
   - 스레드끼리 Transaction이 격리되지 않아 실패
3. `givenMultiThreadAndTransaction_whenUpdated_thenSuccess`
   - 20개의 멀티 스레드를 이용해서 티켓 수량 100개 감소 시키기.
   - 스레드끼리 비관적 락을 통해 격리되어 성공
