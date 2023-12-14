package com.solsol.lock.stopwatch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.StopWatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TestRepository {



    @Test
    @DisplayName("메인 스레드가 멀티 스레드 작업이 다 끝날 때까지 기다리는지 테스트")
    public void multiThreadTest() throws InterruptedException {
        StopWatch stopWatch = new StopWatch(); // 스톱워치 객체 생성
        stopWatch.start(); // 스톱워치 시작
        int threadCount = 20; // 멀티 스레드 생성
        int requestCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        // 스레드는 countDown을 호출해서 requestCount를 하나씩 감소시킴
        CountDownLatch countDownLatch = new CountDownLatch(requestCount);

        for (int i = 0; i < requestCount; i++) {
            executorService.submit(() -> { // submit 안에 함수는 스레드가 실행시킴
                try {

                } finally {
                    countDownLatch.countDown();
                } });
        }

        // 메인 스레드는 requestCount가 0이 될때까지 blocked된다.
        countDownLatch.await();
        stopWatch.stop(); // 스톱워치 스탑
        System.out.println(stopWatch.prettyPrint());
//        assertEquals();
    }
}
